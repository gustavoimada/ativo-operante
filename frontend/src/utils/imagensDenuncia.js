import api from '../services/api'

const CAMPOS_LISTA = [
  'fotos',
  'imagens',
  'imagensUrl',
  'fotosUrl',
  'arquivos',
  'anexos'
]

const CAMPOS_UNITARIOS = [
  'foto',
  'imagem',
  'fotoUrl',
  'imagemUrl',
  'urlFoto',
  'urlImagem',
  'caminhoFoto',
  'caminhoImagem'
]

const CAMPOS_OBJETO = [
  'url',
  'src',
  'caminho',
  'path',
  'arquivo',
  'nome',
  'filename',
  'fileName',
  'nomeArquivo',
  'originalName',
  'originalname'
]

const CAMPOS_NOME = [
  'nome',
  'filename',
  'fileName',
  'nomeArquivo',
  'originalName',
  'originalname',
  'descricao'
]

export function imagensDaDenuncia(denuncia) {
  if (!denuncia) {
    return []
  }

  const imagens = []

  CAMPOS_LISTA.forEach((campo) => {
    adicionarImagem(imagens, denuncia[campo])
  })

  CAMPOS_UNITARIOS.forEach((campo) => {
    adicionarImagem(imagens, denuncia[campo])
  })

  return imagens
    .map((imagem) => normalizarImagem(imagem))
    .filter(Boolean)
    .filter((imagem, indice, lista) => lista.findIndex((item) => item.url === imagem.url) === indice)
}

function adicionarImagem(imagens, valor) {
  if (!valor) {
    return
  }

  if (Array.isArray(valor)) {
    imagens.push(...valor)
    return
  }

  imagens.push(valor)
}

function normalizarImagem(imagem) {
  if (!imagem) {
    return null
  }

  if (typeof imagem === 'string') {
    return {
      url: montarUrl(imagem),
      nome: nomeArquivo(imagem)
    }
  }

  if (typeof imagem === 'object') {
    const caminho = CAMPOS_OBJETO.map((campo) => imagem[campo]).find(Boolean)

    if (!caminho) {
      return null
    }

    return {
      url: montarUrl(caminho),
      nome: CAMPOS_NOME.map((campo) => imagem[campo]).find(Boolean) || nomeArquivo(caminho)
    }
  }

  return null
}

function nomeArquivo(caminho) {
  const texto = String(caminho || '').split('?')[0].split('#')[0]
  const partes = texto.split(/[\\/]/)
  const nome = partes[partes.length - 1]

  return nome || 'imagem-denuncia'
}

function montarUrl(caminho) {
  const texto = String(caminho || '').trim().replace(/\\/g, '/')

  if (!texto) {
    return ''
  }

  if (/^(https?:|data:|blob:)/i.test(texto)) {
    return texto
  }

  if (texto.startsWith('uploads/')) {
    return `${window.location.origin}/${texto}`
  }

  const baseUrl = String(api.defaults.baseURL || '').replace(/\/$/, '')
  const caminhoNormalizado = texto.startsWith('/') ? texto : `/${texto}`

  return `${baseUrl}${caminhoNormalizado}`
}
