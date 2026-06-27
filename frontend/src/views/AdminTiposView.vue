<template>
  <main class="view">
    <PageHeader
      eyebrow="Administrador"
      titulo="Tipos de Problema"
      descricao="Cadastre, edite e remova os tipos usados nas denuncias."
    >
      <template #actions>
        <div class="header-actions">
          <label class="search-field">
            Buscar
            <input v-model="busca" type="search" placeholder="Digite para filtrar">
          </label>
          <button v-if="!formAberto" type="button" @click="abrirNovo">Novo +</button>
        </div>
      </template>
    </PageHeader>

    <MensagemAlerta :mensagem="mensagem" :tipo="tipoMensagem" />

    <section v-if="formAberto" class="panel">
      <form class="inline-form" @submit.prevent="salvar">
        <label>
          Nome
          <input v-model="tipo.nome" type="text" required>
        </label>

        <div class="actions">
          <button type="submit">{{ tipo.id ? 'Atualizar' : 'Cadastrar' }}</button>
          <button class="button secondary" type="button" @click="limpar">Cancelar</button>
        </div>
      </form>
    </section>

    <section class="table-list">
      <article v-for="item in tipos" :key="item.id" class="list-item">
        <div>
          <h2>{{ item.nome }}</h2>
          <p class="muted">ID: {{ item.id }}</p>
        </div>
        <div class="actions">
          <button class="button secondary" type="button" @click="editar(item)">Editar</button>
          <button class="button danger" type="button" @click="apagar(item.id)">Apagar</button>
        </div>
      </article>
      <p v-if="!tipos.length" class="empty-state">Nenhum tipo encontrado.</p>
    </section>

    <ConfirmacaoModal
      :aberto="confirmacaoApagar.aberto"
      titulo="Apagar tipo"
      mensagem="Tem certeza que deseja apagar este tipo?"
      @confirmar="confirmarApagar"
      @cancelar="cancelarApagar"
    />
  </main>
</template>

<script>
import ConfirmacaoModal from '../components/ConfirmacaoModal.vue'
import MensagemAlerta from '../components/MensagemAlerta.vue'
import PageHeader from '../components/PageHeader.vue'
import tipoService from '../services/tipoService'
import { validarTextoObrigatorioSemNumeros } from '../utils/validacao'

export default {
  name: 'AdminTiposView',
  components: {
    ConfirmacaoModal,
    MensagemAlerta,
    PageHeader
  },
  data() {
    return {
      tipos: [],
      tipo: {
        id: null,
        nome: ''
      },
      formAberto: false,
      busca: '',
      timerBusca: null,
      confirmacaoApagar: {
        aberto: false,
        id: null
      },
      mensagem: '',
      tipoMensagem: 'info'
    }
  },
  async mounted() {
    await this.carregar()
  },
  unmounted() {
    clearTimeout(this.timerBusca)
  },
  watch: {
    busca() {
      clearTimeout(this.timerBusca)
      this.timerBusca = setTimeout(() => {
        this.carregar()
      }, 300)
    }
  },
  methods: {
    async carregar() {
      try {
        const resposta = await tipoService.listarTodos(this.busca)
        this.tipos = resposta.data || []
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel carregar os tipos.'
      }
    },
    async salvar() {
      const erroValidacao = validarTextoObrigatorioSemNumeros(this.tipo.nome, 'Nome')

      if (erroValidacao) {
        this.tipoMensagem = 'erro'
        this.mensagem = erroValidacao
        return
      }

      const nome = this.tipo.nome.trim()

      if (await this.nomeRepetido(nome)) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Ja existe um tipo com esse nome.'
        return
      }

      try {
        if (this.tipo.id) {
          await tipoService.atualizar({
            id: this.tipo.id,
            nome
          })
        } else {
          await tipoService.salvar({ nome })
        }

        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Tipo salvo com sucesso.'
        this.limpar()
        await this.carregar()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel salvar o tipo.'
      }
    },
    editar(tipo) {
      this.tipo = {
        id: tipo.id,
        nome: tipo.nome || ''
      }
      this.formAberto = true
      this.mensagem = ''
    },
    async apagar(id) {
      this.confirmacaoApagar = {
        aberto: true,
        id
      }
    },
    async confirmarApagar() {
      try {
        await tipoService.apagar(this.confirmacaoApagar.id)
        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Tipo apagado com sucesso.'
        this.cancelarApagar()
        await this.carregar()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel apagar o tipo.'
      }
    },
    cancelarApagar() {
      this.confirmacaoApagar = {
        aberto: false,
        id: null
      }
    },
    limpar() {
      this.tipo = {
        id: null,
        nome: ''
      }
      this.formAberto = false
    },
    abrirNovo() {
      this.tipo = {
        id: null,
        nome: ''
      }
      this.formAberto = true
      this.mensagem = ''
    },
    async nomeRepetido(nome) {
      const nomeNormalizado = this.normalizarNome(nome)
      let tipos = this.tipos

      try {
        const resposta = await tipoService.listarTodos(nome)
        tipos = resposta.data || []
      } catch (erro) {
        tipos = this.tipos
      }

      return tipos.some((item) => {
        return String(item.id) !== String(this.tipo.id) && this.normalizarNome(item.nome) === nomeNormalizado
      })
    },
    normalizarNome(valor) {
      return String(valor || '').trim().toLowerCase()
    }
  }
}
</script>
