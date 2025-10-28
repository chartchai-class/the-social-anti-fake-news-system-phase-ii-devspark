package com.devspark.antifakenews.repository;

import com.devspark.antifakenews.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Comment entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Find comments by news ID
     * 
     * @param newsId the news ID
     * @param pageable pagination information
     * @return page of comments for the specified news
     */
    Page<Comment> findByNewsIdAndIsApprovedTrue(Long newsId, Pageable pageable);

    /**
     * Find comments by user ID
     * 
     * @param userId the user ID
     * @param pageable pagination information
     * @return page of comments by the specified user
     */
    Page<Comment> findByUserId(Long userId, Pageable pageable);

    /**
     * Find top-level comments (no parent)
     * 
     * @param newsId the news ID
     * @param pageable pagination information
     * @return page of top-level comments
     */
    @Query("SELECT c FROM Comment c WHERE c.news.id = :newsId AND c.parentComment IS NULL AND c.isApproved = true")
    Page<Comment> findTopLevelCommentsByNewsId(@Param("newsId") Long newsId, Pageable pageable);

    /**
     * Find replies to a comment
     * 
     * @param parentCommentId the parent comment ID
     * @param pageable pagination information
     * @return page of replies
     */
    Page<Comment> findByParentCommentIdAndIsApprovedTrue(Long parentCommentId, Pageable pageable);

    /**
     * Count comments by news ID
     * 
     * @param newsId the news ID
     * @return count of comments for the specified news
     */
    long countByNewsIdAndIsApprovedTrue(Long newsId);

    /**
     * Find comments by vote type
     * 
     * @param voteType the vote type
     * @param pageable pagination information
     * @return page of comments with the specified vote type
     */
    Page<Comment> findByVoteTypeAndIsApprovedTrue(Comment.VoteType voteType, Pageable pageable);

    /**
     * Find recent comments
     * 
     * @param pageable pagination information
     * @return page of recent comments
     */
    Page<Comment> findByIsApprovedTrueOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Find comments needing approval
     * 
     * @param pageable pagination information
     * @return page of comments needing approval
     */
    Page<Comment> findByIsApprovedFalse(Pageable pageable);

    /**
     * Find all comments for a news article
     * 
     * @param newsId the news ID
     * @return list of all comments for the news
     */
    List<Comment> findByNewsIdAndIsApprovedTrueOrderByCreatedAtAsc(Long newsId);
}
