package com.devspark.antifakenews.repository;

import com.devspark.antifakenews.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Vote entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Find vote by user and news
     * 
     * @param userId the user ID
     * @param newsId the news ID
     * @return Optional containing the vote if found
     */
    Optional<Vote> findByUserIdAndNewsId(Long userId, Long newsId);

    /**
     * Check if user has voted on news
     * 
     * @param userId the user ID
     * @param newsId the news ID
     * @return true if user has voted
     */
    boolean existsByUserIdAndNewsId(Long userId, Long newsId);

    /**
     * Find votes by news ID
     * 
     * @param newsId the news ID
     * @return list of votes for the specified news
     */
    List<Vote> findByNewsId(Long newsId);

    /**
     * Find votes by user ID
     * 
     * @param userId the user ID
     * @return list of votes by the specified user
     */
    List<Vote> findByUserId(Long userId);

    /**
     * Count votes by news ID and vote type
     * 
     * @param newsId the news ID
     * @param voteType the vote type
     * @return count of votes with the specified type
     */
    long countByNewsIdAndVoteType(Long newsId, Vote.VoteType voteType);

    /**
     * Find votes by vote type
     * 
     * @param voteType the vote type
     * @return list of votes with the specified type
     */
    List<Vote> findByVoteType(Vote.VoteType voteType);

    /**
     * Delete vote by user and news
     * 
     * @param userId the user ID
     * @param newsId the news ID
     */
    void deleteByUserIdAndNewsId(Long userId, Long newsId);

    /**
     * Count total votes for a news article
     * 
     * @param newsId the news ID
     * @return total vote count
     */
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.news.id = :newsId")
    long countTotalVotesByNewsId(@Param("newsId") Long newsId);

    /**
     * Get vote statistics for a news article
     * 
     * @param newsId the news ID
     * @return array with [fakeVotes, notFakeVotes]
     */
    @Query("SELECT " +
           "SUM(CASE WHEN v.voteType = 'FAKE' THEN 1 ELSE 0 END), " +
           "SUM(CASE WHEN v.voteType = 'NOT_FAKE' THEN 1 ELSE 0 END) " +
           "FROM Vote v WHERE v.news.id = :newsId")
    Object[] getVoteStatisticsByNewsId(@Param("newsId") Long newsId);
}
