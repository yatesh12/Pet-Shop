package com.petshop.catalog.repository;

import com.petshop.catalog.entity.BlogPost;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Optional<BlogPost> findBySlugAndPublishedTrue(String slug);
    Page<BlogPost> findByPublishedTrue(Pageable pageable);
}

