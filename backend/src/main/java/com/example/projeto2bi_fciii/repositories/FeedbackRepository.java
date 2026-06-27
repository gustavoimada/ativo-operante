package com.example.projeto2bi_fciii.repositories;

import com.example.projeto2bi_fciii.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>
{

}
