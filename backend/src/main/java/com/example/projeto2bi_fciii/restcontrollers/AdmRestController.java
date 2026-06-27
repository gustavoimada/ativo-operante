package com.example.projeto2bi_fciii.restcontrollers;


import com.example.projeto2bi_fciii.entities.Denuncia;
import com.example.projeto2bi_fciii.entities.Erro;
import com.example.projeto2bi_fciii.entities.Feedback;
import com.example.projeto2bi_fciii.entities.Orgao;
import com.example.projeto2bi_fciii.entities.Tipo;
import com.example.projeto2bi_fciii.services.DenunciaService;
import com.example.projeto2bi_fciii.services.OrgaoService;
import com.example.projeto2bi_fciii.services.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis/adm")
public class AdmRestController
{
    // INSTANCIOANDO OS SERVICES

    @Autowired
    private TipoService tipoService;

    @Autowired
    private OrgaoService orgaoService;

    @Autowired
    private DenunciaService denunciaService;

    // ============================ CRUD TIPOS ============================

    // INSERIR
    @PostMapping("tipos")
    public ResponseEntity<Object> add(@RequestBody Tipo tipo)
    {
        tipo = tipoService.save(tipo);
        if(tipo != null)
            return ResponseEntity.ok(tipo);
        return ResponseEntity.badRequest().body(new Erro("Erro ao inserir o tipo"));
    }

    // ALTERAR
    @PutMapping("tipos")
    public ResponseEntity<Object> update(@RequestBody Tipo tipo)
    {
        tipo = tipoService.save(tipo);
        if(tipo != null)
            return ResponseEntity.ok(tipo);
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar o tipo"));
    }

    // DELETAR
    @DeleteMapping("tipos/{id}")
    public ResponseEntity<Object> deleteTipo(@PathVariable Long id)
    {
        if(tipoService.delete(id))
            return ResponseEntity.noContent().build(); // se deu certo, retorna o 200 (status)
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o tipo"));
    }

    // Listar um pelo ID
    @GetMapping("get-tipo/{id}")
    public ResponseEntity<Object> getTipoById(@PathVariable Long id)
    {
        Tipo tipo = tipoService.getId(id);
        if(tipo != null)
            return ResponseEntity.ok(tipo);
        return ResponseEntity.badRequest().body(new Erro("Tipo não existe"));
    }

    // Listar todos
    @GetMapping("get-all-tipos")
    public ResponseEntity<Object> getAllTipos()
    {
        return ResponseEntity.ok(tipoService.getAll());
    }

    // Listar pela keyword do tipo

    @GetMapping("get-by-keyword-tipos/{kw}")
    public ResponseEntity<Object> getAllKeyWordTipo(@PathVariable String kw){
        return ResponseEntity.ok(tipoService.getByKeyWord(kw));
    }

    // ============================ CRUD ORGAOS ============================

    // INSERIR
    @PostMapping("orgaos")
    public ResponseEntity<Object> add(@RequestBody Orgao orgao)
    {
        orgao = orgaoService.save(orgao);
        if(orgao != null)
            return ResponseEntity.ok(orgao);
        return ResponseEntity.badRequest().body(new Erro("Erro ao inserir o orgao"));
    }

    // ALTERAR
    @PutMapping("orgaos")
    public ResponseEntity<Object> update(@RequestBody Orgao orgao)
    {
        orgao = orgaoService.save(orgao);
        if(orgao != null)
            return ResponseEntity.ok(orgao);
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar o orgao"));
    }

    // DELETAR
    @DeleteMapping("orgaos/{id}")
    public ResponseEntity<Object> deleteOrgao(@PathVariable Long id)
    {
        if(orgaoService.delete(id))
            return ResponseEntity.noContent().build(); // se deu certo, retorna o 200 (status)
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar o orgao"));
    }

    // Listar um pelo ID
    @GetMapping("get-orgao/{id}")
    public ResponseEntity<Object> getOrgaoById(@PathVariable Long id)
    {
        Orgao orgao = orgaoService.getId(id);
        if(orgao != null)
            return ResponseEntity.ok(orgao);
        return ResponseEntity.badRequest().body(new Erro("Orgao não existe"));
    }

    // Listar todos
    @GetMapping("get-all-orgaos")
    public ResponseEntity<Object> getAllOrgaos()
    {
        return ResponseEntity.ok(orgaoService.getAll());
    }

    // Listar pela keyword do orgao

    @GetMapping("get-by-keyword-orgaos/{kw}")
    public ResponseEntity<Object> getAllKeyWordOrgao(@PathVariable String kw){
        return ResponseEntity.ok(orgaoService.getByKeyWord(kw));
    }

    // ============================ ROTAS RELACIONADAS À DENUNCIAS ============================

    // Listar todas as denúncias
    @GetMapping("get-all-denuncias")
    public ResponseEntity<Object> getAllDenuncias()
    {
        return ResponseEntity.ok(denunciaService.getAll());
    }

    // Excluir denúncia
    @DeleteMapping("denuncias/{id}")
    public ResponseEntity<Object> deleteDenuncia(@PathVariable Long id)
    {
        if(denunciaService.delete(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar a denuncia"));
    }

    // Registrar feedback em denuncia
    @PutMapping("denuncias/{id}/feedback")
    public ResponseEntity<Object> registraFeedback(@PathVariable Long id, @RequestBody Feedback feedback)
    {
        feedback = denunciaService.registrarFeedback(id, feedback);

        if(feedback != null)
            return ResponseEntity.ok(feedback);

        return ResponseEntity.badRequest().body(new Erro("Erro ao registrar feedback"));
    }
}
