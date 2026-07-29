import { DatePipe } from '@angular/common';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { OPERACOES, ORDEM_OPERACOES } from '../../../core/constants/operacoes';
import { DEMO_FUNCIONARIOS, DEMO_HISTORICO } from '../../../core/data/demo-data';
import {
  HistoricoApi,
  Operacao,
  Registro,
  UsuarioSessao,
} from '../../../core/models/ponto.models';
import { AuthService } from '../../../core/services/auth.service';
import { HistoricoService } from '../../../core/services/historico.service';
import { dataLocal, mapearRegistros } from '../../../core/utils/registro.mapper';
import { AppHeader } from '../../../shared/components/app-header/app-header';
import { DashboardHero } from '../../../shared/components/dashboard-hero/dashboard-hero';
import { HistoryExplorer } from '../../../shared/components/history-explorer/history-explorer';

@Component({
  selector: 'app-funcionario-page',
  imports: [DatePipe, AppHeader, DashboardHero, HistoryExplorer],
  templateUrl: './funcionario-page.html',
})
export class FuncionarioPage implements OnInit {
  private readonly auth = inject(AuthService);
  private readonly historicoService = inject(HistoricoService);

  readonly usuario = this.auth.usuario() as UsuarioSessao;
  readonly operacoes = OPERACOES;
  readonly ordemOperacoes = ORDEM_OPERACOES;
  readonly registros = signal<Registro[]>([]);
  readonly carregando = signal(true);
  readonly erroRegistro = signal('');
  readonly mensagemRegistro = signal('');
  readonly registrandoOperacao = signal<Operacao | null>(null);
  readonly resumo = signal({ registros: 0, funcionarios: 0 });

  readonly ultimaAtualizacao = computed(() => {
    const datas = this.registros().map((item) =>
      new Date(item.dataHoraOperacao).getTime(),
    );
    return datas.length ? new Date(Math.max(...datas)) : null;
  });

  readonly proximaOperacao = computed<Operacao>(() => {
    const hoje = dataLocal(new Date().toISOString());
    const operacoesHoje = this.registros()
      .filter((item) => dataLocal(item.dataHoraOperacao) === hoje)
      .sort(
        (a, b) =>
          new Date(a.dataHoraOperacao).getTime() -
          new Date(b.dataHoraOperacao).getTime(),
      );
    const ultima = operacoesHoje.at(-1)?.operacao;
    if (!ultima || ultima === 'EXPEDIENTE_FIM') return 'EXPEDIENTE_INICIO';
    return ORDEM_OPERACOES[ORDEM_OPERACOES.indexOf(ultima) + 1];
  });

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.carregando.set(true);
    if (this.usuario.demo) {
      const historico = DEMO_HISTORICO.filter(
        (item) => item.funcionario_id === this.usuario.id,
      );
      this.definirRegistros(historico);
      return;
    }

    this.historicoService
      .consultarPorFuncionario(this.usuario.id)
      .subscribe({
        next: (historico) => this.definirRegistros(historico),
        error: () => {
          this.carregando.set(false);
          this.erroRegistro.set('Não foi possível carregar seu histórico.');
        },
      });
  }

  registrarPonto(operacao: Operacao): void {
    this.erroRegistro.set('');
    this.mensagemRegistro.set('');
    this.registrandoOperacao.set(operacao);

    if (!navigator.geolocation) {
      this.registrandoOperacao.set(null);
      this.erroRegistro.set('Este navegador não oferece acesso à localização.');
      return;
    }

    navigator.geolocation.getCurrentPosition(
      ({ coords }) =>
        this.enviarRegistro(
          operacao,
          coords.latitude.toString(),
          coords.longitude.toString(),
        ),
      () => {
        this.registrandoOperacao.set(null);
        this.erroRegistro.set(
          'Não foi possível obter sua localização. Permita o acesso e tente novamente.',
        );
      },
      { enableHighAccuracy: true, timeout: 12000, maximumAge: 30000 },
    );
  }

  private enviarRegistro(
    operacao: Operacao,
    latitude: string,
    longitude: string,
  ): void {
    if (this.usuario.demo) {
      const registro: HistoricoApi = {
        id: `demo-${Date.now()}`,
        funcionario_id: this.usuario.id,
        operacao,
        latitude,
        longitude,
        dataHoraOperacao: new Date().toISOString(),
      };
      const historicoAtual = this.registros().map(
        ({ funcionarioNome: _nome, funcionarioEmail: _email, lat: _lat, lng: _lng, ...item }) =>
          item,
      );
      this.registros.set(
        mapearRegistros([registro, ...historicoAtual], [
          {
            id: this.usuario.id,
            nome: this.usuario.nome,
            email: this.usuario.email,
          },
        ]),
      );
      this.concluirRegistro(operacao);
      return;
    }

    this.historicoService
      .registrar({
        funcionario_id: this.usuario.id,
        operacao,
        latitude,
        longitude,
      })
      .subscribe({
        next: () => {
          this.concluirRegistro(operacao);
          this.carregarDados();
        },
        error: (erro) => {
          this.registrandoOperacao.set(null);
          this.erroRegistro.set(
            erro?.error?.message ||
              erro?.error?.mensagem ||
              'Não foi possível registrar o ponto.',
          );
        },
      });
  }

  private definirRegistros(historico: HistoricoApi[]): void {
    const funcionario =
      DEMO_FUNCIONARIOS.find((item) => item.id === this.usuario.id) ?? {
        id: this.usuario.id,
        nome: this.usuario.nome,
        email: this.usuario.email,
      };
    this.registros.set(mapearRegistros(historico, [funcionario]));
    this.carregando.set(false);
  }

  private concluirRegistro(operacao: Operacao): void {
    this.registrandoOperacao.set(null);
    this.mensagemRegistro.set(
      `${this.operacoes[operacao].label} registrado com sucesso.`,
    );
  }
}
