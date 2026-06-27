package com.example.projeto2bi_fciii.repositories;

import com.example.projeto2bi_fciii.entities.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long>
{
    public List<Tipo> findAllByNomeContainingIgnoreCase(String kw);
}
