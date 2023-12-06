package com.example.todolist.items.entity;

import com.example.todolist.enums.Status;
import com.example.todolist.todolists.entity.TodoList;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_list_id"))
    private TodoList todoList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Objects.equals(this.id, item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.id);
    }

}
