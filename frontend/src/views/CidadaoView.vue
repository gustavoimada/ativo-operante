<template>
  <main class="view">
    <PageHeader
      eyebrow="Area do cidadao"
      titulo="Minhas Denuncias"
      descricao="Acompanhe suas denuncias e veja os feedbacks recebidos."
    >
      <template #actions>
        <RouterLink class="button-link" to="/cidadao/nova-denuncia">Nova denuncia</RouterLink>
      </template>
    </PageHeader>

    <MensagemAlerta :mensagem="mensagem" :tipo="tipoMensagem" />

    <section v-if="denuncias.length" class="table-list">
      <article v-for="denuncia in denuncias" :key="denuncia.id" class="list-item denuncia-card">
        <div class="denuncia-content">
          <div class="denuncia-card-header">
            <div>
              <h2>{{ denuncia.titulo }}</h2>
              <p>{{ denuncia.texto }}</p>
            </div>
            <span class="status-badge status-success">{{ denuncia.status || 'Enviada' }}</span>
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

          <p class="feedback-box" :class="temFeedback(denuncia) ? 'feedback-success' : 'feedback-pending'">
            {{ textoFeedback(denuncia) || 'Ainda nao existe feedback para esta denuncia.' }}
          </p>
        </div>
      </article>
    </section>

    <div v-else class="empty-state">
      Nenhuma denuncia encontrada. Use o botao Nova denuncia para cadastrar a primeira.
    </div>
  </main>
</template>

<script>
import MensagemAlerta from '../components/MensagemAlerta.vue'
import PageHeader from '../components/PageHeader.vue'
import denunciaService from '../services/denunciaService'
import { imagensDaDenuncia } from '../utils/imagensDenuncia'

export default {
  name: 'CidadaoView',
  components: {
    MensagemAlerta,
    PageHeader
  },
  data() {
    return {
      denuncias: [],
      mensagem: '',
      tipoMensagem: 'info'
    }
  },
  async mounted() {
    await this.carregarDenuncias()
  },
  methods: {
    async carregarDenuncias() {
      try {
        const resposta = await denunciaService.minhasDenuncias()
        this.denuncias = resposta.data || []
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel carregar suas denuncias.'
      }
    },
    nomeCampo(valor) {
      if (!valor) {
        return ''
      }

      return valor.nome || valor
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
