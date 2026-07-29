import { CommonModule, DatePipe } from '@angular/common';
import {
  Component,
  computed,
  effect,
  input,
  output,
  signal,
  viewChild,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OPERACOES } from '../../../core/constants/operacoes';
import { Operacao, Perfil, Registro } from '../../../core/models/ponto.models';
import { dataLocal } from '../../../core/utils/registro.mapper';
import { PontoMap } from '../ponto-map/ponto-map';

@Component({
  selector: 'app-history-explorer',
  imports: [CommonModule, FormsModule, DatePipe, PontoMap],
  templateUrl: './history-explorer.html',
})
export class HistoryExplorer {
  readonly registros = input<Registro[]>([]);
  readonly perfil = input.required<Perfil>();
  readonly carregando = input(false);
  readonly resumoAlterado = output<{
    registros: number;
    funcionarios: number;
  }>();

  readonly operacoes = OPERACOES;
  readonly busca = signal('');
  readonly filtroOperacao = signal<'TODAS' | Operacao>('TODAS');
  readonly dataFiltro = signal('');
  readonly selecionadoId = signal<string | null>(null);
  readonly mapa = viewChild(PontoMap);

  readonly filtrados = computed(() => {
    const termo = this.busca().trim().toLocaleLowerCase('pt-BR');
    const operacao = this.filtroOperacao();
    const data = this.dataFiltro();

    return this.registros().filter((registro) => {
      const correspondeBusca =
        !termo ||
        registro.funcionarioNome.toLocaleLowerCase('pt-BR').includes(termo) ||
        this.operacoes[registro.operacao].label
          .toLocaleLowerCase('pt-BR')
          .includes(termo);
      const correspondeOperacao =
        operacao === 'TODAS' || registro.operacao === operacao;
      const correspondeData =
        !data || dataLocal(registro.dataHoraOperacao) === data;
      return correspondeBusca && correspondeOperacao && correspondeData;
    });
  });

  readonly selecionado = computed(
    () =>
      this.registros().find(
        (registro) => registro.id === this.selecionadoId(),
      ) ?? null,
  );

  constructor() {
    effect(() => {
      const registros = this.registros();
      const selecionadoExiste = registros.some(
        (registro) => registro.id === this.selecionadoId(),
      );
      if (!selecionadoExiste) {
        this.selecionadoId.set(registros[0]?.id ?? null);
      }
    });
    effect(() => {
      const filtrados = this.filtrados();
      this.resumoAlterado.emit({
        registros: filtrados.length,
        funcionarios: new Set(
          filtrados.map((registro) => registro.funcionario_id),
        ).size,
      });
    });
  }

  atualizarOperacao(valor: string): void {
    this.filtroOperacao.set(valor as 'TODAS' | Operacao);
  }

  selecionar(registro: Registro): void {
    this.selecionadoId.set(registro.id);
    this.mapa()?.focar(registro);
  }

  limparFiltros(): void {
    this.busca.set('');
    this.filtroOperacao.set('TODAS');
    this.dataFiltro.set('');
  }

  formatarCoordenada(valor: number): string {
    return valor.toFixed(5);
  }
}
