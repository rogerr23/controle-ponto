export type Perfil = 'FUNCIONARIO' | 'GESTOR';

export type Operacao =
  | 'EXPEDIENTE_INICIO'
  | 'ALMOÇO_INICIO'
  | 'ALMOÇO_FIM'
  | 'EXPEDIENTE_FIM';

export interface UsuarioSessao {
  id: string;
  nome: string;
  email: string;
  perfil: Perfil;
  demo?: boolean;
}

export interface Credenciais {
  email: string;
  senha: string;
}

export interface HistoricoApi {
  id: string;
  funcionario_id: string;
  operacao: Operacao;
  latitude: string;
  longitude: string;
  dataHoraOperacao: string;
}

export interface FuncionarioApi {
  id: string;
  nome: string;
  email: string;
}

export interface NovoFuncionario {
  nome: string;
  email: string;
  senha: string;
}

export interface NovoRegistro {
  funcionario_id: string;
  operacao: Operacao;
  latitude: string;
  longitude: string;
}

export interface Registro extends HistoricoApi {
  funcionarioNome: string;
  funcionarioEmail?: string;
  lat: number;
  lng: number;
}

export interface OperacaoInfo {
  label: string;
  curta: string;
  cor: string;
  classe: string;
}
