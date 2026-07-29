import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  FuncionarioApi,
  NovoFuncionario,
} from '../models/ponto.models';

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  private readonly http = inject(HttpClient);

  consultar(): Observable<FuncionarioApi[]> {
    return this.http.get<FuncionarioApi[]>('/api/funcionario/consultar');
  }

  cadastrar(funcionario: NovoFuncionario): Observable<FuncionarioApi> {
    return this.http.post<FuncionarioApi>(
      '/api/funcionario/cadastrar',
      funcionario,
    );
  }
}
