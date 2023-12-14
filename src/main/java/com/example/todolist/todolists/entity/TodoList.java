package com.example.todolist.todolists.entity;

import com.example.todolist.items.entity.Item;
import com.example.todolist.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_creator_id"))
    private User creator;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "todoList")
    private Set<Item> items;

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TodoList todoList = (TodoList) o;
        return Objects.equals(this.id, todoList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.id);
    }

}
