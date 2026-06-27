import api from './api'

export default {
  logar(login, senha) {
    return api.post('/acesso/logar', null, {
      params: {
        login,
        senha
      }
    })
  },

  cadastrar(dados) {
    return api.post('/acesso/cadastrar', dados)
  }
}
