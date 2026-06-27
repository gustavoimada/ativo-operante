function decodeJwtPayload(token) {
  if (!token || typeof token !== 'string') {
    return null
  }

  const partes = token.split('.')
  if (partes.length !== 3) {
    return null
  }

  try {
    const base64Url = partes[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const preenchimento = '='.repeat((4 - (base64.length % 4)) % 4)
    const payloadJson = atob(base64 + preenchimento)
    return JSON.parse(payloadJson)
  } catch (erro) {
    return null
  }
}

export function getPerfilFromToken(token) {
  const payload = decodeJwtPayload(token)
  const nivel = payload && payload.nivel ? String(payload.nivel).toUpperCase() : ''

  if (nivel === 'ADM') {
    return 'admin'
  }

  if (nivel === 'CIDADAO') {
    return 'cidadao'
  }

  return ''
}

