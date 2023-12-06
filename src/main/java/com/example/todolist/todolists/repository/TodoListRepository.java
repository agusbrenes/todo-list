package com.example.todolist.todolists.repository;

import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Integer> {

    Optional<TodoList> findByCreatorAndId(User creator, Integer id);

    Integer countAllByCreator(User creator);

    Page<TodoList> findAllByCreatorOrderByCreatedAtAsc(User creator, Pageable paging);

}
