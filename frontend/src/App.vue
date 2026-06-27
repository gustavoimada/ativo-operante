<template>
  <div class="app-shell">
    <MenuPrincipal />
    <RouterView />
  </div>
</template>

<script>
import MenuPrincipal from './components/MenuPrincipal.vue'

export default {
  name: 'App',
  components: {
    MenuPrincipal
  }
}
</script>

<style>
* {
  box-sizing: border-box;
}

body {
  background:
    radial-gradient(circle at top left, rgba(23, 107, 135, 0.14), transparent 34rem),
    linear-gradient(180deg, #f7fafc 0%, #eef4f8 100%);
  margin: 0;
  min-height: 100vh;
}

#app {
  font-family: Inter, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #17202a;
}

a {
  color: inherit;
}

.app-shell {
  min-height: 100vh;
}

.menu-shell {
  align-items: center;
  background: rgba(255, 255, 255, 0.94);
  border-bottom: 1px solid rgba(196, 211, 224, 0.76);
  box-shadow: 0 8px 24px rgba(25, 38, 52, 0.06);
  display: flex;
  gap: 20px;
  justify-content: space-between;
  min-height: 68px;
  padding: 0 32px;
  position: sticky;
  top: 0;
  z-index: 10;
  backdrop-filter: blur(10px);
}

.brand {
  align-items: center;
  color: #102a43;
  display: inline-flex;
  font-weight: 800;
  gap: 10px;
  text-decoration: none;
}

.brand-mark {
  align-items: center;
  background: linear-gradient(135deg, #176b87, #124559);
  border-radius: 8px;
  box-shadow: 0 8px 18px rgba(23, 107, 135, 0.24);
  color: #ffffff;
  display: inline-flex;
  height: 36px;
  justify-content: center;
  width: 36px;
}

.menu-principal {
  align-items: center;
  display: flex;
  gap: 8px;
}

.menu-principal a,
.sair {
  background: transparent;
  border: 0;
  border-radius: 8px;
  color: #3e5065;
  cursor: pointer;
  font: inherit;
  font-size: 14px;
  font-weight: 700;
  line-height: 1;
  padding: 11px 13px;
  text-decoration: none;
  transition: background 0.18s ease, box-shadow 0.18s ease, color 0.18s ease, transform 0.18s ease;
}

.menu-principal a:hover {
  background: #f0f6f8;
  color: #123f52;
}

.menu-principal a.router-link-active {
  background: #dff0f5;
  box-shadow: inset 0 0 0 1px #b7dce8;
  color: #124559;
}

.sair {
  align-items: center;
  background: #fff5f5;
  border: 1px solid #ffd0d0;
  color: #9b2c2c;
  display: inline-flex;
  margin-left: 6px;
}

.sair:hover {
  background: #b42318;
  border-color: #b42318;
  box-shadow: 0 8px 18px rgba(180, 35, 24, 0.18);
  color: #ffffff;
  transform: translateY(-1px);
}

.menu-toggle {
  display: none;
}

.view {
  margin: 0 auto;
  max-width: 1180px;
  padding: 42px 24px 54px;
  width: 100%;
}

.panel {
  background: #ffffff;
  border: 1px solid rgba(204, 217, 229, 0.86);
  border-radius: 8px;
  box-shadow: 0 18px 42px rgba(25, 38, 52, 0.1);
  padding: 28px;
  width: 100%;
}

.auth-layout {
  align-items: start;
  display: grid;
  gap: 28px;
  grid-template-columns: minmax(0, 1fr) 420px;
}

.auth-intro {
  padding: 36px 0;
}

.auth-intro h1 {
  color: #102a43;
  font-size: 46px;
  line-height: 1.08;
  margin-bottom: 14px;
}

.auth-intro p {
  color: #536274;
  font-size: 18px;
  line-height: 1.6;
  max-width: 620px;
}

.page-header {
  align-items: end;
  display: flex;
  gap: 20px;
  justify-content: space-between;
  margin-bottom: 22px;
}

.page-header h1 {
  margin-bottom: 8px;
}

.page-eyebrow {
  color: #16627d;
  font-size: 13px;
  font-weight: 800;
  letter-spacing: 0;
  margin-bottom: 8px;
  text-transform: uppercase;
}

.page-description {
  color: #536274;
  line-height: 1.5;
  margin: 0;
}

.page-actions {
  align-items: end;
  display: flex;
  flex-shrink: 0;
}

.header-actions {
  align-items: end;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  justify-content: flex-end;
}

.search-field {
  min-width: 220px;
}

h1,
h2,
h3,
p {
  margin-top: 0;
}

h1 {
  font-size: 30px;
  line-height: 1.15;
}

h2 {
  font-size: 20px;
}

h3 {
  font-size: 17px;
}

.form {
  display: grid;
  gap: 16px;
  margin: 22px 0;
}

.form.grid {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

label {
  color: #253447;
  display: grid;
  font-size: 14px;
  font-weight: 700;
  gap: 6px;
}

input,
select,
textarea {
  background: #ffffff;
  border: 1px solid #c8d4df;
  border-radius: 6px;
  font: inherit;
  padding: 10px 12px;
  transition: border-color 0.18s ease, box-shadow 0.18s ease;
  width: 100%;
}

input:focus,
select:focus,
textarea:focus {
  border-color: #176b87;
  box-shadow: 0 0 0 3px rgba(23, 107, 135, 0.14);
  outline: 0;
}

textarea {
  resize: vertical;
}

.field-hint {
  color: #647386;
  font-size: 13px;
  font-weight: 600;
}

.selected-files {
  color: #34465c;
  display: grid;
  font-size: 13px;
  gap: 4px;
  list-style: none;
  margin: 2px 0 0 0;
  padding-left: 0;
  width: 100%;
}

.selected-files li {
  align-items: center;
  background: #f7fafc;
  border: 1px solid #d9e3ec;
  border-radius: 6px;
  display: flex;
  gap: 10px;
  justify-content: space-between;
  overflow-wrap: anywhere;
  padding: 7px 9px;
}

.remove-file-button {
  background: #fff5f5;
  border: 1px solid #ffd0d0;
  color: #9b2c2c;
  flex-shrink: 0;
  font-size: 12px;
  padding: 6px 9px;
}

button,
.button,
.button-link {
  background: #176b87;
  border: 0;
  border-radius: 6px;
  color: #ffffff;
  cursor: pointer;
  display: inline-block;
  font: inherit;
  font-weight: 700;
  padding: 11px 16px;
  text-align: center;
  text-decoration: none;
  transition: background 0.18s ease, box-shadow 0.18s ease, transform 0.18s ease;
}

.button:hover,
.button-link:hover,
button:hover {
  filter: brightness(0.96);
  transform: translateY(-1px);
}

.button.secondary,
.button-link.secondary {
  background: #eef3f7;
  color: #253447;
}

.button.danger,
.button-link.danger {
  background: #b42318;
}

.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 22px;
}

.full {
  grid-column: 1 / -1;
}

.columns {
  display: grid;
  gap: 24px;
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.cards-grid {
  display: grid;
  gap: 18px;
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.dashboard-grid {
  align-items: start;
  display: grid;
  gap: 22px;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  margin-bottom: 24px;
}

.dashboard-section {
  display: grid;
  gap: 16px;
  margin-bottom: 24px;
}

.section-title {
  align-items: center;
  display: flex;
  gap: 14px;
  justify-content: space-between;
}

.section-title h2 {
  margin: 0;
}

.denuncia-title-row {
  align-items: center;
  display: flex;
  flex-wrap: wrap;
  gap: 10px 16px;
}

.denuncia-title-row h2 {
  margin: 0;
}

.denuncia-field {
  display: grid;
  gap: 4px;
}

.admin-report-main {
  display: grid;
  gap: 12px;
}

.admin-title-line {
  align-items: start;
  display: flex;
  gap: 18px;
  justify-content: space-between;
}

.denuncia-field span,
.reported-by strong {
  color: #647386;
  font-size: 11px;
  font-weight: 800;
  letter-spacing: 0;
  text-transform: uppercase;
}

.reported-by {
  align-items: center;
  background: #f7fafc;
  border: 1px solid #d9e3ec;
  border-radius: 6px;
  color: #34465c;
  display: inline-flex;
  flex-wrap: wrap;
  gap: 6px;
  margin: 0;
  padding: 8px 10px;
}

.description-box {
  background: #fbfdff;
  border-left: 3px solid #b8c9d9;
  padding-left: 12px;
}

.description-box p {
  color: #17202a;
}

.inline-form {
  align-items: end;
  display: grid;
  gap: 12px;
  grid-template-columns: minmax(0, 1fr) auto;
}

.inline-form .actions {
  margin-top: 0;
}

.panel .table-list {
  margin-top: 18px;
}

.card {
  background: #ffffff;
  border: 1px solid rgba(204, 217, 229, 0.9);
  border-radius: 8px;
  box-shadow: 0 12px 30px rgba(25, 38, 52, 0.07);
  padding: 20px;
}

.card p {
  color: #5b6878;
  line-height: 1.45;
}

.table-list {
  display: grid;
  gap: 12px;
}

.list-item {
  align-items: start;
  background: #ffffff;
  border: 1px solid rgba(204, 217, 229, 0.9);
  border-radius: 6px;
  box-shadow: 0 10px 26px rgba(25, 38, 52, 0.06);
  display: grid;
  gap: 14px;
  grid-template-columns: minmax(0, 1fr) auto;
  padding: 16px;
}

.muted {
  color: #647386;
}

.meta {
  display: grid;
  gap: 10px;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  margin-top: 16px;
}

.meta span {
  border-radius: 6px;
  color: #34465c;
  display: grid;
  font-size: 14px;
  gap: 4px;
  min-height: 58px;
  padding: 9px 11px;
}

.meta .meta-date,
.meta .meta-urgency,
.meta .meta-type,
.meta .meta-agency {
  background: #f7fafc;
  border: 1px solid #d9e3ec;
}

.meta strong {
  color: #647386;
  font-size: 11px;
  letter-spacing: 0;
  text-transform: uppercase;
}

.denuncia-files {
  background: #f7fafc;
  border: 1px solid #d9e3ec;
  border-radius: 6px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 16px;
  padding: 12px;
}

.denuncia-files > strong {
  color: #647386;
  flex-basis: 100%;
  font-size: 11px;
  letter-spacing: 0;
  text-transform: uppercase;
}

.denuncia-files p {
  flex-basis: 100%;
  margin: 0;
}

.file-download {
  background: #ffffff;
  border: 1px solid #d9e3ec;
  border-radius: 6px;
  color: #123f52;
  cursor: pointer;
  display: inline-flex;
  font-size: 14px;
  font-weight: 700;
  max-width: 100%;
  overflow-wrap: anywhere;
  padding: 9px 11px;
  text-decoration: none;
}

.status-badge {
  align-items: center;
  border-radius: 999px;
  display: inline-flex;
  flex-shrink: 0;
  font-size: 13px;
  font-weight: 800;
  justify-content: center;
  line-height: 1;
  min-height: 34px;
  padding: 9px 13px;
}

.status-success {
  background: #e7f7ee;
  border: 1px solid #bde7cc;
  color: #17613a;
}

.denuncia-card {
  gap: 18px;
  padding: 20px;
}

.denuncia-content {
  min-width: 0;
}

.denuncia-card-header {
  align-items: start;
  display: flex;
  gap: 18px;
  justify-content: space-between;
}

.denuncia-card-header h2 {
  font-size: 22px;
  line-height: 1.2;
  margin-bottom: 8px;
}

.denuncia-card-header p {
  line-height: 1.5;
  margin-bottom: 0;
}

.feedback-form {
  display: grid;
  gap: 12px;
  margin: 18px 0 0;
}

.feedback-admin-box {
  align-items: center;
  border-radius: 6px;
  display: flex;
  gap: 16px;
  justify-content: space-between;
  line-height: 1.45;
  margin-top: 18px;
  padding: 14px;
}

.feedback-admin-box strong {
  display: block;
  margin-bottom: 5px;
}

.feedback-admin-box p {
  color: #17202a;
  margin: 0;
}

.edit-feedback-button {
  background: #ffffff;
  border: 1px solid #b9e4ca;
  color: #17613a;
}

.edit-feedback-button:hover {
  background: #f6fcf8;
}

.denuncia-actions {
  align-items: end;
  justify-content: end;
  margin-top: 0;
}

.feedback-box {
  border-radius: 6px;
  line-height: 1.45;
  margin: 16px 0 0;
  padding: 12px 14px;
}

.feedback-pending {
  background: #fff8e6;
  border: 1px solid #edd08b;
  color: #17202a;
}

.feedback-success {
  background: #e9f7ef;
  border: 1px solid #b9e4ca;
  color: #17202a;
}

.form-actions {
  align-items: center;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.empty-state {
  background: #ffffff;
  border: 1px dashed #b9c7d5;
  border-radius: 8px;
  color: #647386;
  padding: 24px;
  text-align: center;
}

.modal-backdrop {
  align-items: center;
  background: rgba(15, 23, 42, 0.38);
  display: flex;
  inset: 0;
  justify-content: center;
  padding: 20px;
  position: fixed;
  z-index: 60;
}

.modal-dialog {
  background: #ffffff;
  border: 1px solid #d9e3ec;
  border-radius: 8px;
  box-shadow: 0 24px 60px rgba(15, 23, 42, 0.24);
  max-width: 420px;
  padding: 22px;
  width: 100%;
}

.modal-dialog h2 {
  margin-bottom: 8px;
}

.modal-dialog p {
  color: #526173;
  line-height: 1.5;
  margin-bottom: 20px;
}

.modal-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

.alert {
  box-shadow: 0 16px 36px rgba(25, 38, 52, 0.18);
  border-radius: 6px;
  line-height: 1.4;
  margin: 0;
  max-width: min(420px, calc(100vw - 32px));
  padding: 12px 14px;
  position: fixed;
  right: 24px;
  top: 88px;
  width: max-content;
  z-index: 40;
}

.alert-info {
  background: #e7f3f7;
  border: 1px solid #b8dbe7;
  color: #114e63;
}

.alert-sucesso {
  background: #e9f7ef;
  border: 1px solid #b9e4ca;
  color: #17613a;
}

.alert-erro {
  background: #fff0f0;
  border: 1px solid #ffc9c9;
  color: #9b2c2c;
}

.toast-enter-active,
.toast-leave-active {
  transition: opacity 0.18s ease, transform 0.18s ease;
}

.toast-enter-from,
.toast-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}

@media (max-width: 720px) {
  .menu-shell {
    align-items: flex-start;
    padding: 14px 18px;
  }

  .menu-toggle {
    background: #eef3f7;
    color: #253447;
    display: inline-flex;
  }

  .menu-principal {
    background: rgba(255, 255, 255, 0.98);
    border-top: 1px solid #dfe7ef;
    box-shadow: 0 18px 32px rgba(25, 38, 52, 0.1);
    display: none;
    flex-direction: column;
    left: 0;
    padding: 12px 18px 18px;
    position: absolute;
    right: 0;
    top: 64px;
  }

  .menu-principal.aberto {
    display: flex;
  }

  .menu-principal a,
  .sair {
    text-align: left;
    width: 100%;
  }

  .view {
    padding: 24px 16px 38px;
  }

  .auth-layout,
  .form.grid,
  .dashboard-grid,
  .inline-form,
  .columns,
  .cards-grid,
  .meta {
    grid-template-columns: 1fr;
  }

  .auth-intro {
    padding: 0;
  }

  .auth-intro h1 {
    font-size: 34px;
  }

  .page-header {
    align-items: start;
    flex-direction: column;
  }

  .list-item {
    grid-template-columns: 1fr;
  }

  .denuncia-card-header {
    flex-direction: column;
  }

  .denuncia-actions {
    justify-content: start;
  }

  .alert {
    left: 16px;
    right: 16px;
    top: 84px;
    width: auto;
  }
}
</style>
