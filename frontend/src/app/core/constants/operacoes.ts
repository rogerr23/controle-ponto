import { Operacao, OperacaoInfo } from '../models/ponto.models';

export const OPERACOES: Record<Operacao, OperacaoInfo> = {
  EXPEDIENTE_INICIO: {
    label: 'Início do expediente',
    curta: 'Entrada',
    cor: '#168568',
    classe: 'entrada',
  },
  'ALMOÇO_INICIO': {
    label: 'Início do almoço',
    curta: 'Pausa',
    cor: '#d2873d',
    classe: 'pausa',
  },
  'ALMOÇO_FIM': {
    label: 'Fim do almoço',
    curta: 'Retorno',
    cor: '#4b73c3',
    classe: 'retorno',
  },
  EXPEDIENTE_FIM: {
    label: 'Fim do expediente',
    curta: 'Saída',
    cor: '#c95858',
    classe: 'saida',
  },
};

export const ORDEM_OPERACOES: Operacao[] = [
  'EXPEDIENTE_INICIO',
  'ALMOÇO_INICIO',
  'ALMOÇO_FIM',
  'EXPEDIENTE_FIM',
];
