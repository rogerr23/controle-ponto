import {
  FuncionarioApi,
  HistoricoApi,
  UsuarioSessao,
} from '../models/ponto.models';

export const DEMO_HISTORICO: HistoricoApi[] = [
  {
    id: 'demo-1',
    funcionario_id: 'demo-ana',
    operacao: 'EXPEDIENTE_INICIO',
    latitude: '-23.5616',
    longitude: '-46.6562',
    dataHoraOperacao: '2026-07-28T08:03:00-03:00',
  },
  {
    id: 'demo-2',
    funcionario_id: 'demo-carlos',
    operacao: 'EXPEDIENTE_INICIO',
    latitude: '-23.5582',
    longitude: '-46.6621',
    dataHoraOperacao: '2026-07-28T08:12:00-03:00',
  },
  {
    id: 'demo-3',
    funcionario_id: 'demo-ana',
    operacao: 'ALMOÇO_INICIO',
    latitude: '-23.5637',
    longitude: '-46.6544',
    dataHoraOperacao: '2026-07-28T12:06:00-03:00',
  },
  {
    id: 'demo-4',
    funcionario_id: 'demo-marina',
    operacao: 'ALMOÇO_FIM',
    latitude: '-23.5558',
    longitude: '-46.6507',
    dataHoraOperacao: '2026-07-28T13:04:00-03:00',
  },
  {
    id: 'demo-5',
    funcionario_id: 'demo-carlos',
    operacao: 'EXPEDIENTE_FIM',
    latitude: '-23.5597',
    longitude: '-46.6589',
    dataHoraOperacao: '2026-07-28T17:48:00-03:00',
  },
];

export const DEMO_FUNCIONARIOS: FuncionarioApi[] = [
  { id: 'demo-ana', nome: 'Ana Souza', email: 'ana@empresa.com' },
  { id: 'demo-carlos', nome: 'Carlos Lima', email: 'carlos@empresa.com' },
  { id: 'demo-marina', nome: 'Marina Alves', email: 'marina@empresa.com' },
];

export const USUARIO_DEMO_FUNCIONARIO: UsuarioSessao = {
  id: 'demo-ana',
  nome: 'Ana Souza',
  email: 'ana@empresa.com',
  perfil: 'FUNCIONARIO',
  demo: true,
};

export const USUARIO_DEMO_GESTOR: UsuarioSessao = {
  id: 'demo-gestor',
  nome: 'Rafael Gomes',
  email: 'gestor@empresa.com',
  perfil: 'GESTOR',
  demo: true,
};
