<template>
  <Transition name="toast">
    <p v-if="mensagem" class="alert" :class="`alert-${tipo}`" role="alert" aria-live="polite">
      {{ mensagem }}
    </p>
  </Transition>
</template>

<script>
export default {
  name: 'MensagemAlerta',
  props: {
    mensagem: {
      type: String,
      default: ''
    },
    tipo: {
      type: String,
      default: 'info'
    }
  }
}
</script>
