import api from './api'

export default {
  criar(denuncia) {
    return api.post('/apis/cidadao/denuncias', denuncia)
  },

  minhasDenuncias() {
    return api.get('/apis/cidadao/denuncias')
  },

  meusFeedbacks() {
    return api.get('/apis/cidadao/feedbacks')
  },

  listarTodas() {
    return api.get('/apis/adm/get-all-denuncias')
  },

  apagar(id) {
    return api.delete(`/apis/adm/denuncias/${id}`)
  },

  registrarFeedback(id, feedback) {
    return api.put(`/apis/adm/denuncias/${id}/feedback`, feedback)
  }
}
