<template>
  <header class="menu-shell">
    <RouterLink class="brand" :to="homeRoute">
      <span class="brand-mark">AO</span>
      <span>Ativo e Operante</span>
    </RouterLink>

    <button class="menu-toggle" type="button" @click="menuAberto = !menuAberto">
      Menu
    </button>

    <nav class="menu-principal" :class="{ aberto: menuAberto }">
      <RouterLink
        v-for="item in itens"
        :key="item.to"
        :to="item.to"
        @click="menuAberto = false"
      >
        {{ item.label }}
      </RouterLink>
      <button v-if="perfil" class="sair" type="button" @click="sair">Sair</button>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'MenuPrincipal',
  data() {
    return {
      menuAberto: false,
      perfil: localStorage.getItem('perfil') || ''
    }
  },
  computed: {
    homeRoute() {
      if (this.perfil === 'admin') {
        return '/admin'
      }

      if (this.perfil === 'cidadao') {
        return '/cidadao'
      }

      return '/'
    },
    itens() {
      if (this.$route.path === '/' || this.$route.path === '/cadastro') {
        return [
          { label: 'Login', to: '/' },
          { label: 'Criar conta', to: '/cadastro' }
        ]
      }

      if (this.perfil === 'admin') {
        return [
          { label: 'Denuncias', to: '/admin' },
          { label: 'Tipos', to: '/admin/tipos' },
          { label: 'Orgaos', to: '/admin/orgaos' }
        ]
      }

      if (this.perfil === 'cidadao') {
        return [
          { label: 'Denuncias', to: '/cidadao' }
        ]
      }

      return [
        { label: 'Login', to: '/' },
        { label: 'Criar conta', to: '/cadastro' }
      ]
    }
  },
  watch: {
    $route() {
      this.perfil = localStorage.getItem('perfil') || ''
    }
  },
  methods: {
    sair() {
      localStorage.removeItem('token')
      localStorage.removeItem('perfil')
      this.perfil = ''
      this.menuAberto = false
      this.$router.push('/')
    }
  }
}
</script>
