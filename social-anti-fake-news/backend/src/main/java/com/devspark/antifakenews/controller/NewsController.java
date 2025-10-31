package com.devspark.antifakenews.controller;

import com.devspark.antifakenews.dto.NewsDto;
import com.devspark.antifakenews.dto.request.CreateNewsRequest;
import com.devspark.antifakenews.entity.News;
import com.devspark.antifakenews.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for News operations
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "News", description = "News management APIs")
public class NewsController {

    private final NewsService newsService;

    /**
     * Create new news article
     * 
     * @param request the create news request
     * @param authentication the authentication object
     * @return created news DTO
     */
    @PostMapping
    @Operation(summary = "Create news article", description = "Create a new news article")
    public ResponseEntity<NewsDto> createNews(
            @Valid @RequestBody CreateNewsRequest request,
            Authentication authentication) {
        
        log.info("Creating news article: {}", request.getTitle());
        
        // Get user ID from authentication (you'll need to implement this)
        Long userId = getCurrentUserId(authentication);
        
        NewsDto news = newsService.createNews(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    /**
     * Get news by ID
     * 
     * @param id the news ID
     * @return news DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get news by ID", description = "Retrieve a news article by its ID")
    public ResponseEntity<NewsDto> getNewsById(
            @Parameter(description = "News ID") @PathVariable Long id) {
        
        log.info("Fetching news with ID: {}", id);
        
        NewsDto news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    /**
     * Get all news with pagination
     * 
     * @param pageable pagination information
     * @return page of news DTOs
     */
    @GetMapping
    @Operation(summary = "Get all news", description = "Retrieve all news articles with pagination")
    public ResponseEntity<Page<NewsDto>> getAllNews(
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Fetching all news with pagination: {}", pageable);
        
        Page<NewsDto> news = newsService.getAllNews(pageable);
        return ResponseEntity.ok(news);
    }

    /**
     * Search news by term
     * 
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching news DTOs
     */
    @GetMapping("/search")
    @Operation(summary = "Search news", description = "Search news articles by title or content")
    public ResponseEntity<Page<NewsDto>> searchNews(
            @Parameter(description = "Search term") @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Searching news with term: {}", searchTerm);
        
        Page<NewsDto> news = newsService.searchNews(searchTerm, pageable);
        return ResponseEntity.ok(news);
    }

    /**
     * Get news by status
     * 
     * @param status the news status
     * @param pageable pagination information
     * @return page of news DTOs with the specified status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get news by status", description = "Retrieve news articles by status")
    public ResponseEntity<Page<NewsDto>> getNewsByStatus(
            @Parameter(description = "News status") @PathVariable News.NewsStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Fetching news with status: {}", status);
        
        Page<NewsDto> news = newsService.getNewsByStatus(status, pageable);
        return ResponseEntity.ok(news);
    }

    /**
     * Get trending news
     * 
     * @param pageable pagination information
     * @return page of trending news DTOs
     */
    @GetMapping("/trending")
    @Operation(summary = "Get trending news", description = "Retrieve trending news articles")
    public ResponseEntity<Page<NewsDto>> getTrendingNews(
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Fetching trending news");
        
        Page<NewsDto> news = newsService.getTrendingNews(pageable);
        return ResponseEntity.ok(news);
    }

    /**
     * Update news status
     * 
     * @param id the news ID
     * @param status the new status
     * @return updated news DTO
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "Update news status", description = "Update the status of a news article")
    public ResponseEntity<NewsDto> updateNewsStatus(
            @Parameter(description = "News ID") @PathVariable Long id,
            @Parameter(description = "New status") @RequestParam News.NewsStatus status) {
        
        log.info("Updating news status for ID: {} to {}", id, status);
        
        NewsDto news = newsService.updateNewsStatus(id, status);
        return ResponseEntity.ok(news);
    }

    /**
     * Delete news
     * 
     * @param id the news ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete news", description = "Delete a news article")
    public ResponseEntity<Void> deleteNews(
            @Parameter(description = "News ID") @PathVariable Long id) {
        
        log.info("Deleting news with ID: {}", id);
        
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Get news statistics
     * 
     * @return statistics map
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get news statistics", description = "Retrieve news statistics")
    public ResponseEntity<Map<String, Long>> getNewsStatistics() {
        log.info("Fetching news statistics");
        
        Map<String, Long> statistics = newsService.getNewsStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get news by reporter
     * 
     * @param reporterId the reporter ID
     * @param pageable pagination information
     * @return page of news DTOs by the reporter
     */
    @GetMapping("/reporter/{reporterId}")
    @Operation(summary = "Get news by reporter", description = "Retrieve news articles by reporter")
    public ResponseEntity<Page<NewsDto>> getNewsByReporter(
            @Parameter(description = "Reporter ID") @PathVariable Long reporterId,
            @PageableDefault(size = 10) Pageable pageable) {
        
        log.info("Fetching news by reporter ID: {}", reporterId);
        
        Page<NewsDto> news = newsService.getNewsByReporter(reporterId, pageable);
        return ResponseEntity.ok(news);
    }

    /**
     * Get current user ID from authentication
     * This is a placeholder - you'll need to implement proper user authentication
     * 
     * @param authentication the authentication object
     * @return user ID
     */
    private Long getCurrentUserId(Authentication authentication) {
        // TODO: Implement proper user authentication
        // For now, return a default user ID
        return 1L;
    }
}
