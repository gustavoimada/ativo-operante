<template>
  <main class="view auth-layout">
    <section class="auth-intro">
      <p class="page-eyebrow">Cadastro de cidadao</p>
      <h1>Crie sua conta para registrar denuncias.</h1>
      <p>
        Com uma conta, voce pode enviar novas ocorrencias e acompanhar os
        feedbacks das suas denuncias.
      </p>
    </section>

    <section class="panel">
      <h2>Nova conta</h2>

      <form class="form" @submit.prevent="cadastrar">
        <label>
          CPF
          <input
            v-model="cidadao.cpf"
            type="text"
            inputmode="numeric"
            maxlength="14"
            placeholder="000.000.000-00"
            autocomplete="off"
            required
            @input="formatarCpf"
          >
        </label>

        <label>
          Email
          <input v-model="cidadao.email" type="email" placeholder="cidadao@email.com" required>
        </label>

        <label>
          Senha
          <input v-model="cidadao.senha" type="password" inputmode="numeric" required>
        </label>

        <button type="submit">Cadastrar</button>
      </form>

      <MensagemAlerta :mensagem="mensagem" :tipo="tipoMensagem" />
    </section>
  </main>
</template>

<script>
import MensagemAlerta from '../components/MensagemAlerta.vue'
import authService from '../services/authService'

export default {
  name: 'CadastroView',
  components: {
    MensagemAlerta
  },
  data() {
    return {
      cidadao: {
        cpf: '',
        email: '',
        senha: ''
      },
      mensagem: '',
      tipoMensagem: 'info'
    }
  },
  methods: {
    async cadastrar() {
      this.mensagem = ''

      try {
        await authService.cadastrar({
          cpf: Number(this.apenasNumeros(this.cidadao.cpf)),
          email: this.cidadao.email,
          senha: Number(this.cidadao.senha)
        })
        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Cadastro realizado. Agora voce ja pode fazer login.'
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel cadastrar. Confira os dados ou ajuste o endpoint no backend.'
      }
    },
    formatarCpf() {
      const numeros = this.apenasNumeros(this.cidadao.cpf).slice(0, 11)

      if (numeros.length > 9) {
        this.cidadao.cpf = `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6, 9)}-${numeros.slice(9)}`
        return
      }

      if (numeros.length > 6) {
        this.cidadao.cpf = `${numeros.slice(0, 3)}.${numeros.slice(3, 6)}.${numeros.slice(6)}`
        return
      }

      if (numeros.length > 3) {
        this.cidadao.cpf = `${numeros.slice(0, 3)}.${numeros.slice(3)}`
        return
      }

      this.cidadao.cpf = numeros
    },
    apenasNumeros(valor) {
      return String(valor || '').replace(/\D/g, '')
    }
  }
}
</script>
