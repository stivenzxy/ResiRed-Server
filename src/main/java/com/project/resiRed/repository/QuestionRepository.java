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

    List<Question> findAllBySurvey(Survey survey);


    Optional<Question> findByCanBeVoted(Boolean canBeVoted);

    List<Question> findBySurveyAndVoted(Survey survey, boolean voted);

    @Query(value="select * from questions where survey_id=:survey and voted=false ORDER BY question_id ASC", nativeQuery = true)
    List<Question> findNextQuestion(Long survey);

}
