<template>
  <main class="view auth-layout">
    <section class="auth-intro">
      <p class="page-eyebrow">Sistema de denuncias urbanas</p>
      <h1>Ativo e Operante</h1>
      <p>
        Registre problemas da cidade, acompanhe o andamento das denuncias e
        consulte o feedback enviado pela administracao.
      </p>
    </section>

    <section class="panel">
      <h2>Entrar</h2>
      <p class="muted">Acesse com seu login e senha cadastrados.</p>

      <form class="form" @submit.prevent="logar">
        <label>
          Login ou email
          <input v-model="login" type="text" placeholder="email@exemplo.com" required>
        </label>

        <label>
          Senha
          <input v-model="senha" type="password" placeholder="Sua senha" required>
        </label>

        <button type="submit">Entrar</button>
      </form>

      <MensagemAlerta :mensagem="mensagem" :tipo="tipoMensagem" />
    </section>
  </main>
</template>

<script>
import MensagemAlerta from '../components/MensagemAlerta.vue'
import authService from '../services/authService'
import { getPerfilFromToken } from '../utils/token'

export default {
  name: 'LoginView',
  components: {
    MensagemAlerta
  },
  data() {
    return {
      login: '',
      senha: '',
      mensagem: '',
      tipoMensagem: 'info'
    }
  },
  methods: {
    async logar() {
      this.mensagem = ''

      try {
        const resposta = await authService.logar(this.login, this.senha)

        const token = resposta.data && resposta.data.token ? resposta.data.token : resposta.data
        const tokenNormalizado = String(token).trim()
        const perfil = getPerfilFromToken(tokenNormalizado)

        if (!perfil) {
          this.tipoMensagem = 'erro'
          this.mensagem = 'Token retornado sem perfil valido. Faca login novamente.'
          localStorage.removeItem('token')
          localStorage.removeItem('perfil')
          return
        }

        localStorage.setItem('token', tokenNormalizado)
        localStorage.setItem('perfil', perfil)

        this.$router.push(perfil === 'admin' ? '/admin' : '/cidadao')
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel fazer login. Verifique os dados e tente novamente.'
      }
    }
  }
}
</script>
