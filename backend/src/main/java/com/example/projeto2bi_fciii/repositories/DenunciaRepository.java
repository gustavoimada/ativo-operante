package com.example.projeto2bi_fciii.repositories;

import com.example.projeto2bi_fciii.entities.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long>
{
    List<Denuncia> findByUsuarioId(Long usuarioId);
}
