package com.example.projeto2bi_fciii.repositories;

import com.example.projeto2bi_fciii.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
    Usuario findByEmail(String email);
    Usuario findByEmailIgnoreCase(String email);
    Usuario findByCpf(Long cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCpf(Long cpf);
}
