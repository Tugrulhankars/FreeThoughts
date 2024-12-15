package com.tugrulhan.freethoughts.repositories;

import com.tugrulhan.freethoughts.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}