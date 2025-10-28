package com.devspark.antifakenews.service;

import com.devspark.antifakenews.dto.NewsDto;
import com.devspark.antifakenews.dto.request.CreateNewsRequest;
import com.devspark.antifakenews.entity.News;
import com.devspark.antifakenews.entity.User;
import com.devspark.antifakenews.mapper.NewsMapper;
import com.devspark.antifakenews.repository.NewsRepository;
import com.devspark.antifakenews.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for News operations
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsMapper newsMapper;

    /**
     * Create new news article
     * 
     * @param request the create news request
     * @param reporterId the reporter ID
     * @return created news DTO
     */
    public NewsDto createNews(CreateNewsRequest request, Long reporterId) {
        log.info("Creating news article: {}", request.getTitle());
        
        User reporter = userRepository.findById(reporterId)
            .orElseThrow(() -> new RuntimeException("Reporter not found"));
        
        News news = News.builder()
            .title(request.getTitle())
            .shortDetail(request.getShortDetail())
            .fullDetail(request.getFullDetail())
            .imageUrl(request.getImageUrl())
            .status(request.getStatus())
            .reporter(reporter)
            .build();
        
        News savedNews = newsRepository.save(news);
        log.info("News article created with ID: {}", savedNews.getId());
        
        return newsMapper.toDto(savedNews);
    }

    /**
     * Get news by ID
     * 
     * @param id the news ID
     * @return news DTO
     */
    @Transactional(readOnly = true)
    public NewsDto getNewsById(Long id) {
        log.info("Fetching news with ID: {}", id);
        
        News news = newsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("News not found"));
        
        return newsMapper.toDto(news);
    }

    /**
     * Get all news with pagination
     * 
     * @param pageable pagination information
     * @return page of news DTOs
     */
    @Transactional(readOnly = true)
    public Page<NewsDto> getAllNews(Pageable pageable) {
        log.info("Fetching all news with pagination: {}", pageable);
        
        Page<News> newsPage = newsRepository.findByOrderByCreatedAtDesc(pageable);
        return newsPage.map(newsMapper::toDto);
    }

    /**
     * Search news by term
     * 
     * @param searchTerm the search term
     * @param pageable pagination information
     * @return page of matching news DTOs
     */
    @Transactional(readOnly = true)
    public Page<NewsDto> searchNews(String searchTerm, Pageable pageable) {
        log.info("Searching news with term: {}", searchTerm);
        
        Page<News> newsPage = newsRepository.searchNews(searchTerm, pageable);
        return newsPage.map(newsMapper::toDto);
    }

    /**
     * Get news by status
     * 
     * @param status the news status
     * @param pageable pagination information
     * @return page of news DTOs with the specified status
     */
    @Transactional(readOnly = true)
    public Page<NewsDto> getNewsByStatus(News.NewsStatus status, Pageable pageable) {
        log.info("Fetching news with status: {}", status);
        
        Page<News> newsPage = newsRepository.findByStatus(status, pageable);
        return newsPage.map(newsMapper::toDto);
    }

    /**
     * Get trending news
     * 
     * @param pageable pagination information
     * @return page of trending news DTOs
     */
    @Transactional(readOnly = true)
    public Page<NewsDto> getTrendingNews(Pageable pageable) {
        log.info("Fetching trending news");
        
        Page<News> newsPage = newsRepository.findTrendingNews(pageable);
        return newsPage.map(newsMapper::toDto);
    }

    /**
     * Update news status
     * 
     * @param id the news ID
     * @param status the new status
     * @return updated news DTO
     */
    public NewsDto updateNewsStatus(Long id, News.NewsStatus status) {
        log.info("Updating news status for ID: {} to {}", id, status);
        
        News news = newsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("News not found"));
        
        news.setStatus(status);
        News savedNews = newsRepository.save(news);
        
        return newsMapper.toDto(savedNews);
    }

    /**
     * Delete news
     * 
     * @param id the news ID
     */
    public void deleteNews(Long id) {
        log.info("Deleting news with ID: {}", id);
        
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("News not found");
        }
        
        newsRepository.deleteById(id);
    }

    /**
     * Get news statistics
     * 
     * @return statistics map
     */
    @Transactional(readOnly = true)
    public java.util.Map<String, Long> getNewsStatistics() {
        log.info("Fetching news statistics");
        
        long totalNews = newsRepository.count();
        long fakeNews = newsRepository.countByStatus(News.NewsStatus.FAKE);
        long realNews = newsRepository.countByStatus(News.NewsStatus.NOT_FAKE);
        long undecidedNews = newsRepository.countByStatus(News.NewsStatus.UNDECIDED);
        
        return java.util.Map.of(
            "total", totalNews,
            "fake", fakeNews,
            "real", realNews,
            "undecided", undecidedNews
        );
    }

    /**
     * Get news by reporter
     * 
     * @param reporterId the reporter ID
     * @param pageable pagination information
     * @return page of news DTOs by the reporter
     */
    @Transactional(readOnly = true)
    public Page<NewsDto> getNewsByReporter(Long reporterId, Pageable pageable) {
        log.info("Fetching news by reporter ID: {}", reporterId);
        
        Page<News> newsPage = newsRepository.findByReporterId(reporterId, pageable);
        return newsPage.map(newsMapper::toDto);
    }
}
