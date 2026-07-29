import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, map, tap } from 'rxjs';
import {
  USUARIO_DEMO_FUNCIONARIO,
  USUARIO_DEMO_GESTOR,
} from '../data/demo-data';
import {
  Credenciais,
  Perfil,
  UsuarioSessao,
} from '../models/ponto.models';

const SESSION_KEY = 'pontoMapaUsuario';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  readonly usuario = signal<UsuarioSessao | null>(this.restaurarSessao());

  entrar(
    perfil: Perfil,
    credenciais: Credenciais,
  ): Observable<UsuarioSessao> {
    const endpoint =
      perfil === 'FUNCIONARIO'
        ? '/api/funcionario/autenticar'
        : '/api/gestor/autenticar';

    return this.http
      .post<Omit<UsuarioSessao, 'perfil'>>(endpoint, {
        email: credenciais.email.trim().toLowerCase(),
        senha: credenciais.senha,
      })
      .pipe(
        map((resposta) => ({ ...resposta, perfil })),
        tap((usuario) => this.definirSessao(usuario)),
      );
  }

  entrarDemo(perfil: Perfil): UsuarioSessao {
    const usuario =
      perfil === 'FUNCIONARIO'
        ? USUARIO_DEMO_FUNCIONARIO
        : USUARIO_DEMO_GESTOR;
    this.definirSessao(usuario);
    return usuario;
  }

  sair(): void {
    sessionStorage.removeItem(SESSION_KEY);
    this.usuario.set(null);
    void this.router.navigate(['/login']);
  }

  private definirSessao(usuario: UsuarioSessao): void {
    const seguro: UsuarioSessao = {
      id: usuario.id,
      nome: usuario.nome,
      email: usuario.email,
      perfil: usuario.perfil,
      demo: usuario.demo,
    };
    sessionStorage.setItem(SESSION_KEY, JSON.stringify(seguro));
    this.usuario.set(seguro);
  }

  private restaurarSessao(): UsuarioSessao | null {
    try {
      const valor = sessionStorage.getItem(SESSION_KEY);
      return valor ? (JSON.parse(valor) as UsuarioSessao) : null;
    } catch {
      sessionStorage.removeItem(SESSION_KEY);
      return null;
    }
  }
}
