package com.project.resiRed.repository;

import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;





@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    //@Query("SELECT q.id, q.description FROM Question q WHERE q.survey.id = :surveyId")
    List<Question> findAllBySurvey(Survey survey);
}
