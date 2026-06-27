import { createRouter, createWebHistory } from 'vue-router'
import { getPerfilFromToken } from '../utils/token'

import LoginView from '../views/LoginView.vue'
import CadastroView from '../views/CadastroView.vue'
import AdminView from '../views/AdminView.vue'
import AdminTiposView from '../views/AdminTiposView.vue'
import AdminOrgaosView from '../views/AdminOrgaosView.vue'
import CidadaoView from '../views/CidadaoView.vue'
import DenunciaFormView from '../views/DenunciaFormView.vue'

const routes = [
  { path: '/', name: 'login', component: LoginView },
  { path: '/cadastro', name: 'cadastro', component: CadastroView },
  { path: '/cidadao', name: 'cidadao', component: CidadaoView, meta: { requerAuth: true, perfil: 'cidadao' } },
  { path: '/cidadao/nova-denuncia', name: 'nova-denuncia', component: DenunciaFormView, meta: { requerAuth: true, perfil: 'cidadao' } },
  { path: '/cidadao/minhas-denuncias', redirect: '/cidadao' },
  { path: '/cidadao/feedbacks', redirect: '/cidadao' },
  { path: '/admin', name: 'admin', component: AdminView, meta: { requerAuth: true, perfil: 'admin' } },
  { path: '/admin/tipos', name: 'admin-tipos', component: AdminTiposView, meta: { requerAuth: true, perfil: 'admin' } },
  { path: '/admin/orgaos', name: 'admin-orgaos', component: AdminOrgaosView, meta: { requerAuth: true, perfil: 'admin' } },
  { path: '/admin/denuncias', redirect: '/admin' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (!to.meta.requerAuth) {
    next()
    return
  }

  const token = localStorage.getItem('token')
  if (!token) {
    localStorage.removeItem('perfil')
    next('/')
    return
  }

  const perfilToken = getPerfilFromToken(token)
  if (!perfilToken) {
    localStorage.removeItem('token')
    localStorage.removeItem('perfil')
    next('/')
    return
  }

  localStorage.setItem('perfil', perfilToken)

  if (to.meta.perfil && to.meta.perfil !== perfilToken) {
    next(perfilToken === 'admin' ? '/admin' : '/cidadao')
    return
  }

  next()
})

export default router
