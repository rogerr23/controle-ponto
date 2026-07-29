import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HistoricoApi, NovoRegistro } from '../models/ponto.models';

@Injectable({ providedIn: 'root' })
export class HistoricoService {
  private readonly http = inject(HttpClient);

  consultarTodos(): Observable<HistoricoApi[]> {
    return this.http.get<HistoricoApi[]>('/api/historico/consultar');
  }

  consultarPorFuncionario(funcionarioId: string): Observable<HistoricoApi[]> {
    return this.http.get<HistoricoApi[]>(
      `/api/historico/obterPorIdDeFuncionario/${funcionarioId}`,
    );
  }

  registrar(registro: NovoRegistro): Observable<HistoricoApi> {
    return this.http.post<HistoricoApi>('/api/historico', registro);
  }
}
