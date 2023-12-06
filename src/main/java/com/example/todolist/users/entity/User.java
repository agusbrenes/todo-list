package com.example.todolist.users.entity;

import com.example.todolist.roles.entity.Role;
import com.example.todolist.todolists.entity.TodoList;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    @Email
    private String email;

    @NotBlank
    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_role_id"))
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "creator")
    private Set<TodoList> todoLists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(this.id, user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.id);
    }

}
