package com.devspark.antifakenews.dto.request;

import com.devspark.antifakenews.entity.Vote;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for voting
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    
    @NotNull(message = "Vote type is required")
    private Vote.VoteType voteType;
    
    @NotNull(message = "News ID is required")
    private Long newsId;
}
