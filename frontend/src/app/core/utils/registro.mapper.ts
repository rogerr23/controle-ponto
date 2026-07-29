import {
  FuncionarioApi,
  HistoricoApi,
  Registro,
} from '../models/ponto.models';

export function mapearRegistros(
  historico: HistoricoApi[],
  funcionarios: FuncionarioApi[],
): Registro[] {
  const pessoas = new Map(funcionarios.map((pessoa) => [pessoa.id, pessoa]));

  return historico
    .map((item) => {
      const pessoa = pessoas.get(item.funcionario_id);
      return {
        ...item,
        funcionarioNome: pessoa?.nome ?? 'Funcionário não identificado',
        funcionarioEmail: pessoa?.email,
        lat: Number(item.latitude.replace(',', '.')),
        lng: Number(item.longitude.replace(',', '.')),
      };
    })
    .filter((item) => Number.isFinite(item.lat) && Number.isFinite(item.lng))
    .sort(
      (a, b) =>
        new Date(b.dataHoraOperacao).getTime() -
        new Date(a.dataHoraOperacao).getTime(),
    );
}

export function dataLocal(data: string): string {
  const valor = new Date(data);
  const ano = valor.getFullYear();
  const mes = String(valor.getMonth() + 1).padStart(2, '0');
  const dia = String(valor.getDate()).padStart(2, '0');
  return `${ano}-${mes}-${dia}`;
}
