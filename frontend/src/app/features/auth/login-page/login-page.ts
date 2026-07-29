import { Component, inject, signal } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Perfil } from '../../../core/models/ponto.models';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.html',
})
export class LoginPage {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  readonly perfil = signal<Perfil>('FUNCIONARIO');
  readonly autenticando = signal(false);
  readonly erro = signal('');
  readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    senha: ['', Validators.required],
  });

  escolherPerfil(perfil: Perfil): void {
    this.perfil.set(perfil);
    this.erro.set('');
  }

  entrar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.erro.set('Informe seu e-mail e sua senha.');
      return;
    }

    this.autenticando.set(true);
    this.erro.set('');
    this.auth.entrar(this.perfil(), this.form.getRawValue()).subscribe({
      next: (usuario) => {
        this.autenticando.set(false);
        void this.router.navigate([
          usuario.perfil === 'GESTOR' ? '/gestor' : '/funcionario',
        ]);
      },
      error: (erro) => {
        this.autenticando.set(false);
        this.erro.set(this.extrairErro(erro));
      },
    });
  }

  entrarDemo(): void {
    const usuario = this.auth.entrarDemo(this.perfil());
    void this.router.navigate([
      usuario.perfil === 'GESTOR' ? '/gestor' : '/funcionario',
    ]);
  }

  private extrairErro(erro: any): string {
    const corpo = erro?.error;
    if (Array.isArray(corpo)) return corpo.join(' ');
    return (
      corpo?.message ||
      corpo?.mensagem ||
      'Não foi possível entrar. Verifique os dados e tente novamente.'
    );
  }
}
