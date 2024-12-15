package com.tugrulhan.freethoughts.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tags")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String tagName;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articles;


    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
