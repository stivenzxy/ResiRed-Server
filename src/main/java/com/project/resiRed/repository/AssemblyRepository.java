package com.project.resiRed.repository;

import com.project.resiRed.entity.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly,Long> {
}
