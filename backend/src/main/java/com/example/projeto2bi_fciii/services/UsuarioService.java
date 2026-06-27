package com.example.projeto2bi_fciii.services;

import com.example.projeto2bi_fciii.entities.Usuario;
import com.example.projeto2bi_fciii.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService
{
    public static final int NIVEL_CIDADAO = 1;
    public static final int NIVEL_ADMIN = 2;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getByEmail(String email)
    {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario getId(Long id)
    {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario cadastrarCidadao(Usuario usuario)
    {
        if(usuario == null || usuario.getEmail() == null || usuario.getEmail().isBlank())
            return null;

        if(usuarioRepository.findByEmail(usuario.getEmail()) != null)
            return null;

        usuario.setId(0L);
        usuario.setNivel(NIVEL_CIDADAO);
        return usuarioRepository.save(usuario);
    }

    public String validarCadastroCidadao(Usuario usuario)
    {
        if(usuario == null)
            return "Dados invalidos";

        if(usuario.getEmail() == null || usuario.getEmail().isBlank())
            return "Informe um email";

        if(usuario.getCpf() == null || usuario.getCpf() <= 0)
            return "Informe um CPF valido";

        String email = usuario.getEmail().trim();

        if(usuarioRepository.existsByEmailIgnoreCase(email))
            return "Email ja cadastrado";

        if(usuarioRepository.existsByCpf(usuario.getCpf()))
            return "CPF ja cadastrado";

        return null;
    }
}
