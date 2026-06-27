<template>
  <main class="view">
    <PageHeader
      eyebrow="Administrador"
      titulo="Orgaos Responsaveis"
      descricao="Cadastre, edite e remova os orgaos que recebem denuncias."
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
          <input v-model="orgao.nome" type="text" required>
        </label>

        <div class="actions">
          <button type="submit">{{ orgao.id ? 'Atualizar' : 'Cadastrar' }}</button>
          <button class="button secondary" type="button" @click="limpar">Cancelar</button>
        </div>
      </form>
    </section>

    <section class="table-list">
      <article v-for="item in orgaos" :key="item.id" class="list-item">
        <div>
          <h2>{{ item.nome }}</h2>
          <p class="muted">ID: {{ item.id }}</p>
        </div>
        <div class="actions">
          <button class="button secondary" type="button" @click="editar(item)">Editar</button>
          <button class="button danger" type="button" @click="apagar(item.id)">Apagar</button>
        </div>
      </article>
      <p v-if="!orgaos.length" class="empty-state">Nenhum orgao encontrado.</p>
    </section>

    <ConfirmacaoModal
      :aberto="confirmacaoApagar.aberto"
      titulo="Apagar orgao"
      mensagem="Tem certeza que deseja apagar este orgao?"
      @confirmar="confirmarApagar"
      @cancelar="cancelarApagar"
    />
  </main>
</template>

<script>
import ConfirmacaoModal from '../components/ConfirmacaoModal.vue'
import MensagemAlerta from '../components/MensagemAlerta.vue'
import PageHeader from '../components/PageHeader.vue'
import orgaoService from '../services/orgaoService'
import { validarTextoObrigatorioSemNumeros } from '../utils/validacao'

export default {
  name: 'AdminOrgaosView',
  components: {
    ConfirmacaoModal,
    MensagemAlerta,
    PageHeader
  },
  data() {
    return {
      orgaos: [],
      orgao: {
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
        const resposta = await orgaoService.listarTodos(this.busca)
        this.orgaos = resposta.data || []
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel carregar os orgaos.'
      }
    },
    async salvar() {
      const erroValidacao = validarTextoObrigatorioSemNumeros(this.orgao.nome, 'Nome')

      if (erroValidacao) {
        this.tipoMensagem = 'erro'
        this.mensagem = erroValidacao
        return
      }

      const nome = this.orgao.nome.trim()

      if (await this.nomeRepetido(nome)) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Ja existe um orgao com esse nome.'
        return
      }

      try {
        if (this.orgao.id) {
          await orgaoService.atualizar({
            id: this.orgao.id,
            nome
          })
        } else {
          await orgaoService.salvar({ nome })
        }

        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Orgao salvo com sucesso.'
        this.limpar()
        await this.carregar()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel salvar o orgao.'
      }
    },
    editar(orgao) {
      this.orgao = {
        id: orgao.id,
        nome: orgao.nome || ''
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
        await orgaoService.apagar(this.confirmacaoApagar.id)
        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Orgao apagado com sucesso.'
        this.cancelarApagar()
        await this.carregar()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel apagar o orgao.'
      }
    },
    cancelarApagar() {
      this.confirmacaoApagar = {
        aberto: false,
        id: null
      }
    },
    limpar() {
      this.orgao = {
        id: null,
        nome: ''
      }
      this.formAberto = false
    },
    abrirNovo() {
      this.orgao = {
        id: null,
        nome: ''
      }
      this.formAberto = true
      this.mensagem = ''
    },
    async nomeRepetido(nome) {
      const nomeNormalizado = this.normalizarNome(nome)
      let orgaos = this.orgaos

      try {
        const resposta = await orgaoService.listarTodos(nome)
        orgaos = resposta.data || []
      } catch (erro) {
        orgaos = this.orgaos
      }

      return orgaos.some((item) => {
        return String(item.id) !== String(this.orgao.id) && this.normalizarNome(item.nome) === nomeNormalizado
      })
    },
    normalizarNome(valor) {
      return String(valor || '').trim().toLowerCase()
    }
  }
}
</script>
