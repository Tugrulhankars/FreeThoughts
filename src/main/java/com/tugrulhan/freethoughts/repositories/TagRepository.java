package com.tugrulhan.freethoughts.repositories;

import com.tugrulhan.freethoughts.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
