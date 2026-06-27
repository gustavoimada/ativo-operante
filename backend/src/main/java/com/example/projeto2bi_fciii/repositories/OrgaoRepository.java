package com.example.projeto2bi_fciii.repositories;

import com.example.projeto2bi_fciii.entities.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long>
{
    public List<Orgao> findAllByNomeContainingIgnoreCase(String kw);
}
