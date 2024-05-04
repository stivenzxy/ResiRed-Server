package com.project.resiRed.repository;

import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {
    @Query("SELECT s FROM Survey s LEFT JOIN s.assembly a WHERE a.status='SCHEDULED' OR a IS NULL ORDER BY s.createdAt DESC")
    List<Survey>findAllEditable();

    @Query("SELECT s FROM Survey s WHERE s.assembly = :assembly")
    List<Survey> findAllByAssembly(Assembly assembly);
}
