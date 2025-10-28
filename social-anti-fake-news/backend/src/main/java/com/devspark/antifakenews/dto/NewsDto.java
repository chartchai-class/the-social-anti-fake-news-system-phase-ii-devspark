package com.devspark.antifakenews.dto;

import com.devspark.antifakenews.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for News entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewsDto extends BaseDto {
    private String title;
    private String shortDetail;
    private String fullDetail;
    private String imageUrl;
    private News.NewsStatus status;
    private UserDto reporter;
    private List<CommentDto> comments;
    private VoteCountDto voteCount;
    private Long commentCount;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteCountDto {
        private Integer fakeVotes;
        private Integer notFakeVotes;
        private Integer totalVotes;
        private Double fakePercentage;
        private Double notFakePercentage;
    }
}
