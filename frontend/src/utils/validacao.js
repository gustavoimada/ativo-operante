export function validarTextoObrigatorioSemNumeros(valor, nomeCampo) {
  const texto = String(valor || '').trim()

  if (!texto) {
    return `${nomeCampo} e obrigatorio.`
  }

  if (/\d/.test(texto)) {
    return `${nomeCampo} nao pode conter numeros.`
  }

  return ''
}
