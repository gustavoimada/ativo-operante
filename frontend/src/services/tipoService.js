import api from './api'

export default {
  listarParaCidadao() {
    return api.get('/apis/cidadao/get-all-tipos')
  },

  listarTodos(keyword = '') {
    const texto = String(keyword || '').trim()

    if (texto) {
      return api.get(`/apis/adm/get-by-keyword-tipos/${encodeURIComponent(texto)}`)
    }

    return api.get('/apis/adm/get-all-tipos')
  },

  buscar(id) {
    return api.get(`/apis/adm/get-tipo/${id}`)
  },

  salvar(tipo) {
    return api.post('/apis/adm/tipos', tipo)
  },

  atualizar(tipo) {
    return api.put('/apis/adm/tipos', tipo)
  },

  apagar(id) {
    return api.delete(`/apis/adm/tipos/${id}`)
  }
}
