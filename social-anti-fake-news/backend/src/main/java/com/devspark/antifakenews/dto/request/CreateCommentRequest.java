package com.devspark.antifakenews.dto.request;

import com.devspark.antifakenews.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating comments
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    
    @NotBlank(message = "Comment text is required")
    private String text;
    
    private String imageUrl;
    
    @NotNull(message = "Vote type is required")
    private Comment.VoteType voteType;
    
    @NotNull(message = "News ID is required")
    private Long newsId;
    
    private Long parentCommentId;
}
