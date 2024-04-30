package com.project.resiRed.repository;

import com.project.resiRed.entity.Assembly;
import com.project.resiRed.enums.AssemblyStatus;
import com.project.resiRed.service.admin.AssemblyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly,Long> {

    @Query(value = "SELECT * FROM assemblies WHERE status = :status LIMIT 1", nativeQuery = true)
    Optional<Assembly> findByStatus(AssemblyStatus status);
}
