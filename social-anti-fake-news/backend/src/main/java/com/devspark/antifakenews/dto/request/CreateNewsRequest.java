package com.devspark.antifakenews.dto.request;

import com.devspark.antifakenews.entity.News;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating news
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    @NotBlank(message = "Short detail is required")
    @Size(max = 500, message = "Short detail must not exceed 500 characters")
    private String shortDetail;
    
    @NotBlank(message = "Full detail is required")
    private String fullDetail;
    
    private String imageUrl;
    
    @Builder.Default
    private News.NewsStatus status = News.NewsStatus.UNDECIDED;
}
