package com.example.todolist.items.repository;

import com.example.todolist.items.entity.Item;
import com.example.todolist.todolists.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByTodoListAndId(TodoList todoList, Integer itemId);

}
