package com.tugrulhan.freethoughts.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id", nullable = false,insertable = false,updatable = false)
    private Article article;
    private Long article_id;
    private LocalDateTime createdDate;
}
