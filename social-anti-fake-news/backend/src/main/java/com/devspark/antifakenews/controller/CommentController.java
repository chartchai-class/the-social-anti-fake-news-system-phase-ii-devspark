package com.devspark.antifakenews.controller;

import com.devspark.antifakenews.entity.Comment;
import com.devspark.antifakenews.repository.CommentRepository;
import com.devspark.antifakenews.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news/{newsId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> softDelete(@PathVariable Long newsId, @PathVariable Long commentId){
        Comment c = commentRepository.findById(commentId).orElseThrow();

        commentRepository.save(c);
        // optional: recalc scores/metadata in News if needed
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{commentId}/restore")
    public ResponseEntity<Void> restore(@PathVariable Long newsId, @PathVariable Long commentId){
        Comment c = commentRepository.findById(commentId).orElseThrow();
        
        commentRepository.save(c);
        return ResponseEntity.noContent().build();
    }
}
