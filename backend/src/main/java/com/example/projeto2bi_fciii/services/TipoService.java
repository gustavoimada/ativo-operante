package com.example.projeto2bi_fciii.services;

import com.example.projeto2bi_fciii.entities.Tipo;
import com.example.projeto2bi_fciii.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoService
{
    // INSTANCIANDO O REPOSITORIO

    @Autowired
    private TipoRepository tipoRepository;

    // INSERIR e ALTERAR
    public Tipo save(Tipo tipo)
    {
        Tipo novotipo;
        try
        {
            novotipo = tipoRepository.save(tipo);
        }
        catch (Exception e)
        {
            novotipo = null;
        }
        return novotipo;
    }

    // DELETAR
    public boolean delete(Long id)
    {
        if(getId(id) == null)
            return false; // não deu pra deletar, não achou
        tipoRepository.deleteById(id); // achou, então deleta e dps retorna true
        return true;
    }

    // LISTAR UM
    public Tipo getId(Long id)
    {
        // esse é o unico metodo "o findbyId" que retorna um opcional, por isso, tem esse or else
        // todos os outos como por exemplo findAllByLocal ele retorna um objeto, não, esse "opcinal"
        Tipo tipo = tipoRepository.findById(id).orElse(null);
        // adicionar aquele orElse porque o q ele retorna não é um Objeto tipo Editora mesmo, ele retorna
        // outro bgl, daí eu coloco um orElse que, se não veio o q eu tava querendo, deu null
        return tipo;
    }

    // LISTAR TODOS
    public List<Tipo> getAll()
    {
        List<Tipo> tipoList = tipoRepository.findAll();
        return tipoList;
    }

    public List<Tipo> getByKeyWord(String kw){
        List<Tipo> tipoList = tipoRepository.findAllByNomeContainingIgnoreCase(kw);
        return tipoList;
    }
}
