<template>
  <main class="view">
    <PageHeader
      eyebrow="Administrador"
      titulo="Denuncias"
      descricao="Analise as denuncias recebidas, registre feedbacks e remova registros quando necessario."
    />

    <MensagemAlerta :mensagem="mensagem" :tipo="tipoMensagem" />

    <section v-if="denuncias.length" class="table-list">
      <article v-for="denuncia in denuncias" :key="denuncia.id" class="list-item denuncia-card">
        <div class="denuncia-content">
          <div class="denuncia-card-header">
            <div class="admin-report-main">
              <div class="admin-title-line">
                <div class="denuncia-field">
                  <span>Titulo da denuncia</span>
                  <h2>{{ denuncia.titulo }}</h2>
                </div>

                <p v-if="emailDenunciante(denuncia)" class="reported-by">
                  <strong>Reportado por</strong>
                  {{ emailDenunciante(denuncia) }}
                </p>
              </div>

              <div class="denuncia-field description-box">
                <span>Descricao</span>
                <p>{{ denuncia.texto }}</p>
              </div>
            </div>

            <button class="button danger" type="button" @click="apagar(denuncia.id)">Apagar</button>
          </div>

          <div class="meta">
            <span v-if="denuncia.data" class="meta-date"><strong>Data</strong>{{ formatarData(denuncia.data) }}</span>
            <span v-if="denuncia.urgencia" class="meta-urgency"><strong>Urgencia</strong>{{ denuncia.urgencia }}</span>
            <span v-if="nomeCampo(denuncia.tipo)" class="meta-type"><strong>Tipo</strong>{{ nomeCampo(denuncia.tipo) }}</span>
            <span v-if="nomeCampo(denuncia.orgao)" class="meta-agency"><strong>Orgao</strong>{{ nomeCampo(denuncia.orgao) }}</span>
          </div>

          <section class="denuncia-files" aria-label="Imagens da denuncia">
            <strong>Imagens enviadas</strong>
            <p v-if="!imagensDenuncia(denuncia).length" class="muted">Nenhuma imagem enviada.</p>
            <a
              v-for="(imagem, indice) in imagensDenuncia(denuncia)"
              :key="imagem.url"
              :href="imagem.url"
              class="file-download"
              target="_blank"
              rel="noopener noreferrer"
            >
              {{ imagem.nome || `Imagem ${indice + 1}` }}
            </a>
          </section>

          <div v-if="temFeedback(denuncia)" class="feedback-admin-box feedback-success">
            <div>
              <strong>Feedback registrado</strong>
              <p>{{ textoFeedback(denuncia) }}</p>
            </div>
          </div>

          <form v-else class="feedback-form" @submit.prevent="registrarFeedback(denuncia)">
            <label>
              Feedback
              <textarea v-model="feedbacks[denuncia.id]" rows="3" placeholder="Resposta para o cidadao"></textarea>
            </label>
            <div class="form-actions">
              <button type="submit">Salvar feedback</button>
            </div>
          </form>
        </div>
      </article>
    </section>

    <div v-else class="empty-state">
      Nenhuma denuncia encontrada.
    </div>

    <ConfirmacaoModal
      :aberto="confirmacaoApagar.aberto"
      titulo="Apagar denuncia"
      mensagem="Tem certeza que deseja apagar esta denuncia?"
      @confirmar="confirmarApagar"
      @cancelar="cancelarApagar"
    />
  </main>
</template>

<script>
import ConfirmacaoModal from '../components/ConfirmacaoModal.vue'
import MensagemAlerta from '../components/MensagemAlerta.vue'
import PageHeader from '../components/PageHeader.vue'
import denunciaService from '../services/denunciaService'
import { imagensDaDenuncia } from '../utils/imagensDenuncia'
import { validarTextoObrigatorioSemNumeros } from '../utils/validacao'

export default {
  name: 'AdminView',
  components: {
    ConfirmacaoModal,
    MensagemAlerta,
    PageHeader
  },
  data() {
    return {
      denuncias: [],
      feedbacks: {},
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
  methods: {
    async carregar() {
      try {
        const resposta = await denunciaService.listarTodas()
        this.denuncias = resposta.data || []
        this.feedbacks = {}
        this.denuncias.forEach((denuncia) => {
          this.feedbacks[denuncia.id] = denuncia.feedback && denuncia.feedback.texto ? denuncia.feedback.texto : ''
        })
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel carregar as denuncias.'
      }
    },
    async registrarFeedback(denuncia) {
      const texto = this.feedbacks[denuncia.id]
      const erroValidacao = validarTextoObrigatorioSemNumeros(texto, 'Feedback')

      if (erroValidacao) {
        this.tipoMensagem = 'erro'
        this.mensagem = erroValidacao
        return
      }

      try {
        await denunciaService.registrarFeedback(denuncia.id, {
          texto: texto.trim()
        })
        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Feedback registrado com sucesso.'
        await this.carregar()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel registrar o feedback.'
      }
    },
    async apagar(id) {
      this.confirmacaoApagar = {
        aberto: true,
        id
      }
    },
    async confirmarApagar() {
      try {
        await denunciaService.apagar(this.confirmacaoApagar.id)
        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Denuncia apagada com sucesso.'
        this.cancelarApagar()
        await this.carregar()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel apagar a denuncia.'
      }
    },
    cancelarApagar() {
      this.confirmacaoApagar = {
        aberto: false,
        id: null
      }
    },
    nomeCampo(valor) {
      if (!valor) {
        return ''
      }

      return valor.nome || valor
    },
    emailDenunciante(denuncia) {
      if (!denuncia) {
        return ''
      }

      return denuncia.email ||
        denuncia.emailCidadao ||
        denuncia.cidadaoEmail ||
        (denuncia.cidadao && denuncia.cidadao.email) ||
        (denuncia.usuario && denuncia.usuario.email) ||
        (denuncia.denunciante && denuncia.denunciante.email) ||
        ''
    },
    textoFeedback(denuncia) {
      return denuncia && denuncia.feedback && denuncia.feedback.texto ? denuncia.feedback.texto : ''
    },
    temFeedback(denuncia) {
      return Boolean(this.textoFeedback(denuncia))
    },
    imagensDenuncia(denuncia) {
      return imagensDaDenuncia(denuncia)
    },
    formatarData(data) {
      const texto = String(data || '')
      const partes = texto.split('-')

      if (partes.length === 3) {
        return `${partes[2]}/${partes[1]}/${partes[0]}`
      }

      return texto
    }
  }
}
</script>
