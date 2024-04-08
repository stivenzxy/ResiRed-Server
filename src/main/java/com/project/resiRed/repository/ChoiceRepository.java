package com.project.resiRed.repository;

import com.project.resiRed.entity.Choice;
import com.project.resiRed.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Long> {
   // @Query("SELECT description FROM Choice c WHERE c.question.id = :questionId")
    List<Choice> findAllByQuestion(Question question);
}
