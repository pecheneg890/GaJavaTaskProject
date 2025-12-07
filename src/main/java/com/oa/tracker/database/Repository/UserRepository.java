package com.oa.tracker.database.Repository;

import com.oa.tracker.database.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, String> {

    User findByEmail(String email);
}
