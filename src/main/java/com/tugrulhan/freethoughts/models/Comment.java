package com.tugrulhan.freethoughts.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String content;
    private String author;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    //private Long articleId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @Version
    private int version;
}
