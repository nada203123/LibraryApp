package com.tekup.LibraryApp.model.library;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    Set<Book> books;
    public Category(String name){
        this.name=name;
    }
}
