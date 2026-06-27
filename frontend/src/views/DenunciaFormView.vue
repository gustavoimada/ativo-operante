<template>
  <main class="view">
    <PageHeader
      eyebrow="Cidadao"
      titulo="Nova Denuncia"
      descricao="Preencha os dados principais da ocorrencia para facilitar o encaminhamento."
    >
      <template #actions>
        <RouterLink class="button-link secondary" to="/cidadao">Voltar</RouterLink>
      </template>
    </PageHeader>

    <section class="panel">
      <form class="form grid" @submit.prevent="enviarDenuncia">
        <label>
          Titulo
          <input v-model="denuncia.titulo" type="text" required>
        </label>

        <label>
          Data
          <input v-model="denuncia.data" type="date" :max="dataHoje" required>
        </label>

        <label>
          Urgencia
          <select v-model="denuncia.urgencia">
            <option v-for="nivel in [1, 2, 3, 4, 5]" :key="nivel" :value="nivel">
              {{ nivel }}
            </option>
          </select>
        </label>

        <label>
          Orgao competente
          <select v-model="denuncia.orgaoId" required>
            <option value="">Selecione</option>
            <option v-for="orgao in orgaos" :key="orgao.id" :value="orgao.id">
              {{ orgao.nome }}
            </option>
          </select>
        </label>

        <label>
          Tipo de problema
          <select v-model="denuncia.tipoId" required>
            <option value="">Selecione</option>
            <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">
              {{ tipo.nome }}
            </option>
          </select>
        </label>

        <label>
          Fotos opcionais
          <input
            ref="fotoInput"
            type="file"
            accept="image/png,image/jpeg,image/jpg,image/webp"
            multiple
            @change="selecionarFotos"
          >
          <span v-if="!fotos.length" class="field-hint">Nenhuma imagem selecionada.</span>
          <span v-else class="field-hint">{{ fotos.length }} imagem(ns) selecionada(s).</span>
          <ul v-if="fotos.length" class="selected-files">
            <li v-for="fotoSelecionada in fotos" :key="chaveFoto(fotoSelecionada)">
              <span>{{ fotoSelecionada.name }}</span>
              <button class="remove-file-button" type="button" @click="removerFoto(fotoSelecionada)">Remover</button>
            </li>
          </ul>
        </label>

        <label class="full">
          Texto
          <textarea v-model="denuncia.texto" rows="5" required></textarea>
        </label>

        <div class="form-actions full">
          <button type="submit">Enviar denuncia</button>
          <RouterLink class="button-link secondary" to="/cidadao">Cancelar</RouterLink>
        </div>
      </form>

      <MensagemAlerta :mensagem="mensagem" :tipo="tipoMensagem" />
    </section>
  </main>
</template>

<script>
import MensagemAlerta from '../components/MensagemAlerta.vue'
import PageHeader from '../components/PageHeader.vue'
import denunciaService from '../services/denunciaService'
import orgaoService from '../services/orgaoService'
import tipoService from '../services/tipoService'
import { validarTextoObrigatorioSemNumeros } from '../utils/validacao'

export default {
  name: 'DenunciaFormView',
  components: {
    MensagemAlerta,
    PageHeader
  },
  data() {
    return {
      orgaos: [],
      tipos: [],
      fotos: [],
      mensagem: '',
      tipoMensagem: 'info',
      dataHoje: this.dataAtual(),
      denuncia: {
        titulo: '',
        texto: '',
        data: '',
        urgencia: 1,
        orgaoId: '',
        tipoId: ''
      }
    }
  },
  async mounted() {
    await this.carregarCombos()
  },
  methods: {
    async carregarCombos() {
      try {
        const [orgaos, tipos] = await Promise.all([
          orgaoService.listarParaCidadao(),
          tipoService.listarParaCidadao()
        ])

        this.orgaos = orgaos.data || []
        this.tipos = tipos.data || []
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel carregar tipos e orgaos. Verifique os endpoints do cidadao.'
      }
    },
    selecionarFotos(event) {
      const arquivos = Array.from(event.target.files || [])

      if (!arquivos.length) {
        return
      }

      const erroArquivo = arquivos.map((arquivo) => this.validarFoto(arquivo)).find(Boolean)

      if (erroArquivo) {
        this.fotos = []
        this.tipoMensagem = 'erro'
        this.mensagem = erroArquivo

        if (this.$refs.fotoInput) {
          this.$refs.fotoInput.value = ''
        }

        return
      }

      this.mensagem = ''
      const fotosCombinadas = [...this.fotos, ...arquivos]
      this.fotos = fotosCombinadas.filter((foto, indice, lista) => {
        return lista.findIndex((item) => this.chaveFoto(item) === this.chaveFoto(foto)) === indice
      })

      if (this.$refs.fotoInput) {
        this.$refs.fotoInput.value = ''
      }
    },
    removerFoto(foto) {
      const chave = this.chaveFoto(foto)
      this.fotos = this.fotos.filter((item) => this.chaveFoto(item) !== chave)
    },
    chaveFoto(foto) {
      return `${foto.name}-${foto.size}-${foto.lastModified}`
    },
    async enviarDenuncia() {
      this.mensagem = ''

      const erroValidacao = this.validarDenuncia()

      if (erroValidacao) {
        this.tipoMensagem = 'erro'
        this.mensagem = erroValidacao
        return
      }

      try {
        const formData = new FormData()
        formData.append('titulo', this.denuncia.titulo.trim())
        formData.append('texto', this.denuncia.texto.trim())
        formData.append('data', this.denuncia.data)
        formData.append('urgencia', this.denuncia.urgencia)
        formData.append('orgaoId', this.denuncia.orgaoId)
        formData.append('tipoId', this.denuncia.tipoId)

        this.fotos.forEach((foto) => {
          formData.append('foto', foto)
        })

        await denunciaService.criar(formData)
        this.tipoMensagem = 'sucesso'
        this.mensagem = 'Denuncia enviada com sucesso.'
        this.limparFormulario()
      } catch (erro) {
        this.tipoMensagem = 'erro'
        this.mensagem = 'Nao foi possivel enviar a denuncia. Ajuste o endpoint ou tente novamente.'
      }
    },
    limparFormulario() {
      this.denuncia = {
        titulo: '',
        texto: '',
        data: '',
        urgencia: 1,
        orgaoId: '',
        tipoId: ''
      }
      this.fotos = []
      if (this.$refs.fotoInput) {
        this.$refs.fotoInput.value = ''
      }
    },
    validarDenuncia() {
      const erroTitulo = validarTextoObrigatorioSemNumeros(this.denuncia.titulo, 'Titulo')

      if (erroTitulo) {
        return erroTitulo
      }

      if (!String(this.denuncia.texto || '').trim()) {
        return 'Texto e obrigatorio.'
      }

      if (!this.denuncia.data) {
        return 'Data e obrigatoria.'
      }

      if (this.denuncia.data > this.dataHoje) {
        return 'A data da denuncia nao pode ser futura.'
      }

      if (!this.denuncia.orgaoId) {
        return 'Orgao competente e obrigatorio.'
      }

      if (!this.denuncia.tipoId) {
        return 'Tipo de problema e obrigatorio.'
      }

      if (this.fotos.length) {
        const erroFoto = this.fotos.map((foto) => this.validarFoto(foto)).find(Boolean)

        if (erroFoto) {
          return erroFoto
        }
      }

      return ''
    },
    validarFoto(arquivo) {
      const tiposPermitidos = ['image/png', 'image/jpeg', 'image/webp']
      const extensoesPermitidas = ['.png', '.jpg', '.jpeg', '.webp']
      const nome = String(arquivo.name || '').toLowerCase()
      const temTipoPermitido = tiposPermitidos.includes(arquivo.type)
      const temExtensaoPermitida = extensoesPermitidas.some((extensao) => nome.endsWith(extensao))

      if (!temTipoPermitido || !temExtensaoPermitida) {
        return 'A foto precisa ser uma imagem PNG, JPG, JPEG ou WEBP.'
      }

      return ''
    },
    dataAtual() {
      const hoje = new Date()
      const ano = hoje.getFullYear()
      const mes = String(hoje.getMonth() + 1).padStart(2, '0')
      const dia = String(hoje.getDate()).padStart(2, '0')

      return `${ano}-${mes}-${dia}`
    }
  }
}
</script>
