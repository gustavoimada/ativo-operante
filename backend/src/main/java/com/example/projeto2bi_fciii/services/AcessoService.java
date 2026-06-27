package com.example.projeto2bi_fciii.services;

import com.example.projeto2bi_fciii.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService
{
    @Autowired
    private UsuarioService usuarioService;

    public Usuario autenticar(String email, int senha)
    {
        Usuario usuario = usuarioService.getByEmail(email);

        if(usuario == null)
            return null;

        if(usuario.getSenha() != senha)
            return null;

        return usuario;
    }

    public String getNivelToken(Usuario usuario)
    {
        if(usuario.getNivel() == UsuarioService.NIVEL_ADMIN)
            return "ADM";

        return "CIDADAO";
    }
}
