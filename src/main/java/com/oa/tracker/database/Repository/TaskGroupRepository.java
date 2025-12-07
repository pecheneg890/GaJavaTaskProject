package com.oa.tracker.database.Repository;

import com.oa.tracker.database.Entity.TaskGroup;
import com.oa.tracker.database.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Integer> {

    @Transactional
    long deleteByIdAndUser(int id, User users);

    public List<TaskGroup> findByUser(User user);

    public Optional<TaskGroup> findByIdAndUser(long id, User user);

}
