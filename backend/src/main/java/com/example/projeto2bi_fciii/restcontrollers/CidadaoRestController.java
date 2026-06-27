package com.example.projeto2bi_fciii.restcontrollers;

import com.example.projeto2bi_fciii.entities.Denuncia;
import com.example.projeto2bi_fciii.entities.Erro;
import com.example.projeto2bi_fciii.entities.Imagem;
import com.example.projeto2bi_fciii.entities.Orgao;
import com.example.projeto2bi_fciii.entities.Tipo;
import com.example.projeto2bi_fciii.entities.Usuario;
import com.example.projeto2bi_fciii.services.DenunciaService;
import com.example.projeto2bi_fciii.services.OrgaoService;
import com.example.projeto2bi_fciii.services.TipoService;
import com.example.projeto2bi_fciii.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@RestController
@RequestMapping("/apis/cidadao")
public class CidadaoRestController
{
    // INSTANCIANDO OS SERVICES

    @Autowired
    private TipoService tipoService;

    @Autowired
    private OrgaoService orgaoService;

    @Autowired
    private DenunciaService denunciaService;

    @Autowired
    private UsuarioService usuarioService;

    // ENVIAR DENUNCIA
    @PostMapping("denuncias")
    public ResponseEntity<Object> cadastrarDenuncia(HttpServletRequest request, @RequestParam String titulo, @RequestParam String texto, @RequestParam String data, @RequestParam int urgencia, @RequestParam Long orgaoId, @RequestParam Long tipoId, @RequestParam(required = false) MultipartFile[] foto)
    {
        // tem que saber o usuario que eh, pq só tem o id dele
        String usuarioEmail = request.getAttribute("usuarioEmail").toString();
        Usuario usuario = usuarioService.getByEmail(usuarioEmail);

        // assim como recupera o orgao e o tipo, pq la no front só pegou o id deles
        Orgao orgao = orgaoService.getId(orgaoId);
        Tipo tipo = tipoService.getId(tipoId);

        // verificacao basica
        if(usuario == null || orgao == null || tipo == null)
            return ResponseEntity.badRequest().body(new Erro("Dados invalidos"));

        // monta o objeto
        Denuncia denuncia = new Denuncia();
        denuncia.setTitulo(titulo);
        denuncia.setTexto(texto);
        denuncia.setData(LocalDate.parse(data));
        denuncia.setUrgencia(urgencia);
        denuncia.setUsuario(usuario);
        denuncia.setOrgao(orgao);
        denuncia.setTipo(tipo);

        if(foto != null && foto.length > 0)
        {
            try
            {
                Path pasta = Paths.get("uploads");
                Files.createDirectories(pasta);

                for(int i = 0; i < foto.length; i++)
                {
                    MultipartFile arquivo = foto[i];

                    if(arquivo != null && !arquivo.isEmpty())
                    {

                        String nomeOriginal = arquivo.getOriginalFilename();

                        if(nomeOriginal == null || nomeOriginal.isBlank())
                            nomeOriginal = "imagem";

                        nomeOriginal = Paths.get(nomeOriginal).getFileName().toString();
                        String nomeArquivo = System.currentTimeMillis() + "_" + i + "_" + nomeOriginal;
                        Path caminhoArquivo = pasta.resolve(nomeArquivo);
                        Files.copy(arquivo.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

                        Imagem imagem = new Imagem();
                        imagem.setCaminho(caminhoArquivo.toString());
                        denuncia.addImagem(imagem);
                    }
                }
            }
            catch (Exception e)
            {
                return ResponseEntity.badRequest().body(new Erro("Erro ao salvar imagem"));
            }
        }

        // salva a denuncia, isso se tiver a foto ou nao pq ela eh opcional
        denuncia = denunciaService.save(denuncia);

        if(denuncia != null)
            return ResponseEntity.ok(denuncia);

        return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar denuncia"));
    }

    // LISTAR ORGAOS COMPETENTES
    @GetMapping("get-all-orgaos")
    public ResponseEntity<Object> getAllOrgaos()
    {
        return ResponseEntity.ok(orgaoService.getAll());
    }

    // LISTAR TIPOS DE PROBLEMAS
    @GetMapping("get-all-tipos")
    public ResponseEntity<Object> getAllTipos()
    {
        return ResponseEntity.ok(tipoService.getAll());
    }

    // LISTAR DENÚNCIAS PARA ESSE USUARIO LOGADO, PEGA DO TOKEN
    @GetMapping("denuncias")
    public ResponseEntity<Object> getDenunciasDoUsuario(HttpServletRequest request)
    {
        String usuarioEmail = request.getAttribute("usuarioEmail").toString();
        Usuario usuario = usuarioService.getByEmail(usuarioEmail);

        if(usuario == null)
            return ResponseEntity.badRequest().body(new Erro("Usuario nao encontrado"));

        return ResponseEntity.ok(denunciaService.getByUsuarioId(usuario.getId()));
    }

    // VISUALIZAR FEEDBACKS DO USUARIO LOGADO
    @GetMapping("feedbacks")
    public ResponseEntity<Object> getFeedbacksDoUsuario(HttpServletRequest request)
    {
        String usuarioEmail = request.getAttribute("usuarioEmail").toString();
        Usuario usuario = usuarioService.getByEmail(usuarioEmail);

        if(usuario == null)
            return ResponseEntity.badRequest().body(new Erro("Usuario nao encontrado"));

        return ResponseEntity.ok(denunciaService.getFeedbacksByUsuarioId(usuario.getId()));
    }
}
