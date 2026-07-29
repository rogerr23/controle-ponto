import { Component, OnInit, computed, inject, signal } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { forkJoin } from 'rxjs';
import { DEMO_FUNCIONARIOS, DEMO_HISTORICO } from '../../../core/data/demo-data';
import {
  FuncionarioApi,
  Registro,
  UsuarioSessao,
} from '../../../core/models/ponto.models';
import { AuthService } from '../../../core/services/auth.service';
import { FuncionarioService } from '../../../core/services/funcionario.service';
import { HistoricoService } from '../../../core/services/historico.service';
import { mapearRegistros } from '../../../core/utils/registro.mapper';
import { AppHeader } from '../../../shared/components/app-header/app-header';
import { DashboardHero } from '../../../shared/components/dashboard-hero/dashboard-hero';
import { HistoryExplorer } from '../../../shared/components/history-explorer/history-explorer';

const SENHA_FORTE =
  /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\S+$).{8,}$/;

@Component({
  selector: 'app-gestor-page',
  imports: [
    ReactiveFormsModule,
    AppHeader,
    DashboardHero,
    HistoryExplorer,
  ],
  templateUrl: './gestor-page.html',
})
export class GestorPage implements OnInit {
  private readonly auth = inject(AuthService);
  private readonly funcionarioService = inject(FuncionarioService);
  private readonly historicoService = inject(HistoricoService);
  private readonly fb = inject(FormBuilder);

  readonly usuario = this.auth.usuario() as UsuarioSessao;
  readonly funcionarios = signal<FuncionarioApi[]>([]);
  readonly registros = signal<Registro[]>([]);
  readonly carregando = signal(true);
  readonly cadastrando = signal(false);
  readonly mostrarSenha = signal(false);
  readonly erroCadastro = signal('');
  readonly mensagemCadastro = signal('');
  readonly erroDados = signal('');
  readonly resumo = signal({ registros: 0, funcionarios: 0 });

  readonly form = this.fb.nonNullable.group({
    nome: ['', [Validators.required, Validators.minLength(8)]],
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.pattern(SENHA_FORTE)]],
  });

  readonly ultimaAtualizacao = computed(() => {
    const datas = this.registros().map((item) =>
      new Date(item.dataHoraOperacao).getTime(),
    );
    return datas.length ? new Date(Math.max(...datas)) : null;
  });

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.carregando.set(true);
    this.erroDados.set('');

    if (this.usuario.demo) {
      this.funcionarios.set([...DEMO_FUNCIONARIOS]);
      this.registros.set(
        mapearRegistros(DEMO_HISTORICO, DEMO_FUNCIONARIOS),
      );
      this.carregando.set(false);
      return;
    }

    forkJoin({
      historico: this.historicoService.consultarTodos(),
      funcionarios: this.funcionarioService.consultar(),
    }).subscribe({
      next: ({ historico, funcionarios }) => {
        const equipe = [...funcionarios].sort((a, b) =>
          a.nome.localeCompare(b.nome),
        );
        this.funcionarios.set(equipe);
        this.registros.set(mapearRegistros(historico, equipe));
        this.carregando.set(false);
      },
      error: () => {
        this.carregando.set(false);
        this.erroDados.set(
          'Não foi possível carregar o histórico da equipe.',
        );
      },
    });
  }

  cadastrarFuncionario(): void {
    this.erroCadastro.set('');
    this.mensagemCadastro.set('');

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.erroCadastro.set(this.mensagemValidacao());
      return;
    }

    const dados = {
      nome: this.form.controls.nome.value.trim(),
      email: this.form.controls.email.value.trim().toLowerCase(),
      senha: this.form.controls.senha.value,
    };

    if (
      this.funcionarios().some(
        (item) => item.email.toLowerCase() === dados.email,
      )
    ) {
      this.erroCadastro.set('Já existe um funcionário com este e-mail.');
      return;
    }

    this.cadastrando.set(true);
    if (this.usuario.demo) {
      this.funcionarios.update((atuais) => [
        ...atuais,
        {
          id: `demo-funcionario-${Date.now()}`,
          nome: dados.nome,
          email: dados.email,
        },
      ]);
      this.finalizarCadastro(
        `${dados.nome} foi adicionado à equipe de demonstração.`,
      );
      return;
    }

    this.funcionarioService.cadastrar(dados).subscribe({
      next: (novo) => {
        this.funcionarios.update((atuais) =>
          [...atuais, novo].sort((a, b) => a.nome.localeCompare(b.nome)),
        );
        this.finalizarCadastro(
          `${novo.nome} foi cadastrado e já pode entrar como funcionário.`,
        );
      },
      error: (erro) => {
        this.cadastrando.set(false);
        this.erroCadastro.set(
          this.extrairErro(
            erro,
            'Não foi possível cadastrar o funcionário.',
          ),
        );
      },
    });
  }

  private mensagemValidacao(): string {
    if (this.form.controls.nome.invalid) {
      return 'O nome precisa ter pelo menos 8 caracteres.';
    }
    if (this.form.controls.email.invalid) {
      return 'Informe um e-mail válido.';
    }
    return 'A senha precisa ter 8 caracteres, com maiúscula, minúscula, número e símbolo.';
  }

  private finalizarCadastro(mensagem: string): void {
    this.cadastrando.set(false);
    this.form.reset();
    this.mensagemCadastro.set(mensagem);
  }

  private extrairErro(erro: any, padrao: string): string {
    const corpo = erro?.error;
    if (Array.isArray(corpo)) return corpo.join(' ');
    return corpo?.message || corpo?.mensagem || padrao;
  }
}
