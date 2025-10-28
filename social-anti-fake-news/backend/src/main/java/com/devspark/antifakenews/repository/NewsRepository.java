package com.devspark.antifakenews.repository;

import com.devspark.antifakenews.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for News entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Find news by status
     * 
     * @param status the news status
     * @param pageable pagination information
     * @return page of news with the specified status
     */
    Page<News> findByStatus(News.NewsStatus status, Pageable pageable);

    /**
     * Find news by reporter
     * 
     * @param reporterId the reporter ID
     * @param pageable pagination information
     * @return page of news by the specified reporter
     */
    Page<News> findByReporterId(Long reporterId, Pageable pageable);

    /**
     * Search news by title or content
     * 
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching news
     */
    @Query("SELECT n FROM News n WHERE " +
           "LOWER(n.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.shortDetail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.fullDetail) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<News> searchNews(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find trending news (most voted)
     * 
     * @param pageable pagination information
     * @return page of trending news
     */
    @Query("SELECT n FROM News n ORDER BY (n.voteCount.fakeVotes + n.voteCount.notFakeVotes) DESC")
    Page<News> findTrendingNews(Pageable pageable);

    /**
     * Find recent news
     * 
     * @param pageable pagination information
     * @return page of recent news
     */
    Page<News> findByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Count news by status
     * 
     * @param status the news status
     * @return count of news with the specified status
     */
    long countByStatus(News.NewsStatus status);

    /**
     * Find news with most comments
     * 
     * @param pageable pagination information
     * @return page of news with most comments
     */
    @Query("SELECT n FROM News n LEFT JOIN n.comments c GROUP BY n.id ORDER BY COUNT(c.id) DESC")
    Page<News> findNewsWithMostComments(Pageable pageable);

    /**
     * Find fake news
     * 
     * @return list of fake news
     */
    List<News> findByStatus(News.NewsStatus status);

    /**
     * Find news by status and search term
     * 
     * @param status the news status
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching news
     */
    @Query("SELECT n FROM News n WHERE n.status = :status AND " +
           "(LOWER(n.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.shortDetail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.fullDetail) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<News> findByStatusAndSearch(@Param("status") News.NewsStatus status, 
                                   @Param("searchTerm") String searchTerm, 
                                   Pageable pageable);
}
