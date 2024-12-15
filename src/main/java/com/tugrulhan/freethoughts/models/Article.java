package com.tugrulhan.freethoughts.models;

import com.tugrulhan.freethoughts.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Article  {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String author;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;
    private Long likeCount;
    private LocalDateTime createdDate=LocalDateTime.now();
    private LocalDateTime updatedDate;
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;


}
