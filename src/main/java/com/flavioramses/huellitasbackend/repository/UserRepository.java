package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
