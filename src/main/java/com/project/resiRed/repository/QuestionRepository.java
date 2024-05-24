package com.project.resiRed.repository;

import com.project.resiRed.entity.Choice;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findAllBySurvey(Survey survey);

    Optional<Question> findByCanBeVoted(boolean canBeVoted);

    @Query("SELECT q FROM Question q JOIN q.users u WHERE q.questionId =:questionId AND u.userId =:userId")
    Optional<Question> findByQuestionIdAndUserId(Long questionId, Long userId);



    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Votes (user_id, question_id) VALUES (:userId, :questionId)", nativeQuery = true)
    void registerUserVote(@Param("userId") Long userId, @Param("questionId") Long questionId);


}
