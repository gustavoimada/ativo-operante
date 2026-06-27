<template>
  <header class="page-header">
    <div>
      <p v-if="eyebrow" class="page-eyebrow">{{ eyebrow }}</p>
      <h1>{{ titulo }}</h1>
      <p v-if="descricao" class="page-description">{{ descricao }}</p>
    </div>
    <div v-if="$slots.actions" class="page-actions">
      <slot name="actions"></slot>
    </div>
  </header>
</template>

<script>
export default {
  name: 'PageHeader',
  props: {
    titulo: {
      type: String,
      required: true
    },
    descricao: {
      type: String,
      default: ''
    },
    eyebrow: {
      type: String,
      default: ''
    }
  }
}
</script>
