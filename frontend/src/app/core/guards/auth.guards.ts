import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const loginGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const usuario = auth.usuario();
  if (!usuario) return true;
  return router.createUrlTree([
    usuario.perfil === 'GESTOR' ? '/gestor' : '/funcionario',
  ]);
};

export const funcionarioGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const usuario = auth.usuario();
  if (!usuario) return router.createUrlTree(['/login']);
  return usuario.perfil === 'FUNCIONARIO'
    ? true
    : router.createUrlTree(['/gestor']);
};

export const gestorGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const usuario = auth.usuario();
  if (!usuario) return router.createUrlTree(['/login']);
  return usuario.perfil === 'GESTOR'
    ? true
    : router.createUrlTree(['/funcionario']);
};
