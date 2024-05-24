package com.project.resiRed.repository;

import com.project.resiRed.entity.Assembly;
import com.project.resiRed.enums.AssemblyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly,Long> {


    Optional<Assembly> findByStatus(AssemblyStatus status);

    Optional<Assembly> findByPasscode(Integer passcode);


    @Query("SELECT a FROM Assembly a WHERE a.status = 'FINISHED' or a.status = 'CANCELED'")
    List<Assembly> findAllHistory();

}
