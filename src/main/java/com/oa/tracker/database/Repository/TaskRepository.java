package com.oa.tracker.database.Repository;

import com.oa.tracker.database.Entity.Task;
import com.oa.tracker.database.Entity.User;
import com.oa.tracker.dto.response.TaskStatResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findByUser(User user);

    public Optional<Task> findByIdAndUser(long id, User user);

    @Transactional
    public long deleteByIdAndUser(long id, User user);

    @Query("select new com.oa.tracker.dto.response.TaskStatResponseDto(t.user.id, t.status, count(t.id)) "+
            "from Task t group by t.user, t.status")
    public List<TaskStatResponseDto> getStatistic();
}
