package com.kamsspace.financialproduct.repositories;

import com.kamsspace.financialproduct.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
