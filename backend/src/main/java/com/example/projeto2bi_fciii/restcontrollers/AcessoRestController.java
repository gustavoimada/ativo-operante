package com.example.projeto2bi_fciii.restcontrollers;

import com.example.projeto2bi_fciii.entities.Erro;
import com.example.projeto2bi_fciii.entities.Usuario;
import com.example.projeto2bi_fciii.security.JWTTokenProvider;
import com.example.projeto2bi_fciii.services.AcessoService;
import com.example.projeto2bi_fciii.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("acesso")
public class AcessoRestController
{
    @Autowired
    private AcessoService acessoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("logar")
    public ResponseEntity<Object> logar(@RequestParam String login, @RequestParam String senha)
    {
        if(login == null || login.isBlank() || senha == null || senha.isBlank())
            return ResponseEntity.badRequest().body(new Erro("Informe login e senha"));

        int senhaNumerica;
        try
        {
            senhaNumerica = Integer.parseInt(senha);
        }
        catch (NumberFormatException e)
        {
            return ResponseEntity.badRequest().body(new Erro("Senha deve ser numerica"));
        }

        Usuario usuario = acessoService.autenticar(login, senhaNumerica);

        if(usuario == null)
            return ResponseEntity.badRequest().body(new Erro("Login ou senha invalidos"));

        String nivel = acessoService.getNivelToken(usuario);
        String token = JWTTokenProvider.createToken(usuario.getEmail(), nivel);

        return ResponseEntity.ok(token);
    }

    @PostMapping("cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody Usuario usuario)
    {
        String erroValidacao = usuarioService.validarCadastroCidadao(usuario);
        if(erroValidacao != null)
            return ResponseEntity.badRequest().body(new Erro(erroValidacao));

        Usuario usuarioSalvo = usuarioService.cadastrarCidadao(usuario);

        if(usuarioSalvo == null)
            return ResponseEntity.badRequest().body(new Erro("Erro ao cadastrar usuario"));

        return ResponseEntity.ok(usuarioSalvo);
    }
}
