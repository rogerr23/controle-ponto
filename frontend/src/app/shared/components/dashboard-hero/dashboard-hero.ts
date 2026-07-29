import { DatePipe } from '@angular/common';
import { Component, input } from '@angular/core';
import { Perfil, UsuarioSessao } from '../../../core/models/ponto.models';

@Component({
  selector: 'app-dashboard-hero',
  imports: [DatePipe],
  templateUrl: './dashboard-hero.html',
})
export class DashboardHero {
  readonly usuario = input.required<UsuarioSessao>();
  readonly perfil = input.required<Perfil>();
  readonly totalRegistros = input.required<number>();
  readonly totalFuncionarios = input.required<number>();
  readonly ultimaAtualizacao = input<Date | null>(null);
}
