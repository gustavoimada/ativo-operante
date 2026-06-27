import api from './api'

export default {
  listarParaCidadao() {
    return api.get('/apis/cidadao/get-all-orgaos')
  },

  listarTodos(keyword = '') {
    const texto = String(keyword || '').trim()

    if (texto) {
      return api.get(`/apis/adm/get-by-keyword-orgaos/${encodeURIComponent(texto)}`)
    }

    return api.get('/apis/adm/get-all-orgaos')
  },

  buscar(id) {
    return api.get(`/apis/adm/get-orgao/${id}`)
  },

  salvar(orgao) {
    return api.post('/apis/adm/orgaos', orgao)
  },

  atualizar(orgao) {
    return api.put('/apis/adm/orgaos', orgao)
  },

  apagar(id) {
    return api.delete(`/apis/adm/orgaos/${id}`)
  }
}
