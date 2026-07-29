import { Component, input, output, signal } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Perfil, UsuarioSessao } from '../../../core/models/ponto.models';

@Component({
  selector: 'app-header',
  templateUrl: './app-header.html',
})
export class AppHeader {
  readonly usuario = input.required<UsuarioSessao>();
  readonly perfil = input.required<Perfil>();
  readonly modoDemo = input(false);
  readonly recarregar = output<void>();
  readonly menuAberto = signal(false);

  constructor(readonly auth: AuthService) {}

  rolar(evento: Event, secao: string): void {
    evento.preventDefault();
    document.getElementById(secao)?.scrollIntoView({ behavior: 'smooth' });
    this.menuAberto.set(false);
  }
}
