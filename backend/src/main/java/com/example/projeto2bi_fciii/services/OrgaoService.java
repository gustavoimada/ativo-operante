package com.example.projeto2bi_fciii.services;

import com.example.projeto2bi_fciii.entities.Orgao;
import com.example.projeto2bi_fciii.repositories.OrgaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgaoService
{
    // INSTANCIANDO O REPOSITORIO

    @Autowired
    private OrgaoRepository orgaoRepository;

    // INSERIR e ALTERAR
    public Orgao save(Orgao orgao)
    {
        Orgao novoOrgao;
        try
        {
            novoOrgao = orgaoRepository.save(orgao);
        }
        catch (Exception e)
        {
            novoOrgao = null;
        }
        return novoOrgao;
    }

    // DELETAR
    public boolean delete(Long id)
    {
        if(getId(id) == null)
            return false; // não deu pra deletar, não achou
        orgaoRepository.deleteById(id); // achou, então deleta e dps retorna true
        return true;
    }

    // LISTAR UM
    public Orgao getId(Long id)
    {
        Orgao orgao = orgaoRepository.findById(id).orElse(null);
        return orgao;
    }

    // LISTAR TODOS
    public List<Orgao> getAll()
    {
        List<Orgao> orgaoList = orgaoRepository.findAll();
        return orgaoList;
    }

    public List<Orgao> getByKeyWord(String kw){
        List<Orgao> orgaoList = orgaoRepository.findAllByNomeContainingIgnoreCase(kw);
        return orgaoList;
    }
}
