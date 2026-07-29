import { DEMO_FUNCIONARIOS, DEMO_HISTORICO } from '../data/demo-data';
import { dataLocal, mapearRegistros } from './registro.mapper';

describe('registro mapper', () => {
  it('should attach employee data and parse coordinates', () => {
    const registros = mapearRegistros(DEMO_HISTORICO, DEMO_FUNCIONARIOS);

    expect(registros.length).toBe(5);
    expect(registros[0].funcionarioNome).toBe('Carlos Lima');
    expect(registros[0].lat).toBeCloseTo(-23.5597);
  });

  it('should format a local date for filtering', () => {
    expect(dataLocal('2026-07-28T12:00:00-03:00')).toBe('2026-07-28');
  });
});
