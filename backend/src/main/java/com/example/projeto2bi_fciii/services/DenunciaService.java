package com.example.projeto2bi_fciii.services;

import com.example.projeto2bi_fciii.entities.Denuncia;
import com.example.projeto2bi_fciii.entities.Feedback;
import com.example.projeto2bi_fciii.repositories.DenunciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DenunciaService
{
    // INSTANCIANDO O REPOSITÓRIO

    @Autowired
    private DenunciaRepository denunciaRepository;

    // INSERIR
    public Denuncia save(Denuncia denuncia)
    {
        Denuncia novaDenuncia;
        try
        {
            novaDenuncia = denunciaRepository.save(denuncia);
        }
        catch (Exception e)
        {
            novaDenuncia = null;
        }
        return novaDenuncia;
    }

    // LISTAR TODAS
    public List<Denuncia> getAll()
    {
        List<Denuncia> denunciaList = denunciaRepository.findAll();
        return denunciaList;
    }

    public List<Denuncia> getByUsuarioId(Long usuarioId)
    {
        return denunciaRepository.findByUsuarioId(usuarioId);
    }

    public List<Feedback> getFeedbacksByUsuarioId(Long usuarioId)
    {
        List<Denuncia> denuncias = getByUsuarioId(usuarioId);
        List<Feedback> feedbacks = new ArrayList<>();

        for(Denuncia denuncia : denuncias)
        {
            if(denuncia.getFeedback() != null)
                feedbacks.add(denuncia.getFeedback());
        }

        return feedbacks;
    }

    public Feedback registrarFeedback(Long denunciaId, Feedback feedback)
    {
        Denuncia denuncia = getId(denunciaId);

        if(denuncia == null || feedback == null || feedback.getTexto() == null || feedback.getTexto().isBlank())
            return null;

        if(denuncia.getFeedback() != null)
            return null;

        feedback.setId(0L);
        feedback.setDenuncia(denuncia);
        denuncia.setFeedback(feedback);

        denuncia = save(denuncia);

        if(denuncia == null)
            return null;

        return denuncia.getFeedback();
    }

    // DELETAR

    public Denuncia getId(Long id)
    {
        Denuncia denuncia = denunciaRepository.findById(id).orElse(null);
        return denuncia;
    }

    public boolean delete(Long id)
    {
        if(getId(id) == null)
            return false;
        denunciaRepository.deleteById(id);
        return true;
    }
}
