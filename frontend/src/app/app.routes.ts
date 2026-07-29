import { Routes } from '@angular/router';
import {
  funcionarioGuard,
  gestorGuard,
  loginGuard,
} from './core/guards/auth.guards';

export const routes: Routes = [
  {
    path: 'login',
    canActivate: [loginGuard],
    loadComponent: () =>
      import('./features/auth/login-page/login-page').then(
        (componente) => componente.LoginPage,
      ),
  },
  {
    path: 'funcionario',
    canActivate: [funcionarioGuard],
    loadComponent: () =>
      import(
        './features/funcionario/funcionario-page/funcionario-page'
      ).then((componente) => componente.FuncionarioPage),
  },
  {
    path: 'gestor',
    canActivate: [gestorGuard],
    loadComponent: () =>
      import('./features/gestor/gestor-page/gestor-page').then(
        (componente) => componente.GestorPage,
      ),
  },
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: '**', redirectTo: 'login' },
];
