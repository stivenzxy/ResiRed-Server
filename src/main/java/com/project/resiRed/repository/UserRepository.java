package com.project.resiRed.repository;

import com.project.resiRed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmail(String email);

    /*@Modifying()
    @Query("update User u set u.name=:name, u.lastname=:lastname, u.address=:address where u.userId = :user_id")
    void updateUser(@Param(value = "user_id") Integer userId, @Param(value = "name") String name, @Param(value = "lastname") String lastname , @Param(value = "address") String address);*/
}
