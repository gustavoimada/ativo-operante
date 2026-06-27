<template>
  <div v-if="aberto" class="modal-backdrop" role="presentation">
    <section class="modal-dialog" role="dialog" aria-modal="true" :aria-labelledby="tituloId">
      <h2 :id="tituloId">{{ titulo }}</h2>
      <p>{{ mensagem }}</p>
      <div class="modal-actions">
        <button class="button secondary" type="button" @click="$emit('cancelar')">Cancelar</button>
        <button class="button danger" type="button" @click="$emit('confirmar')">Apagar</button>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'ConfirmacaoModal',
  props: {
    aberto: {
      type: Boolean,
      default: false
    },
    titulo: {
      type: String,
      default: 'Confirmar exclusao'
    },
    mensagem: {
      type: String,
      default: 'Tem certeza que deseja apagar este registro?'
    }
  },
  emits: ['confirmar', 'cancelar'],
  computed: {
    tituloId() {
      return `confirmacao-${this._uid}`
    }
  }
}
</script>
