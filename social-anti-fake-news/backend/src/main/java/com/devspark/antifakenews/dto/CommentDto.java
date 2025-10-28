package com.devspark.antifakenews.dto;

import com.devspark.antifakenews.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Comment entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentDto extends BaseDto {
    private String text;
    private String imageUrl;
    private Comment.VoteType voteType;
    private UserDto user;
    private Long newsId;
    private Long parentCommentId;
    private List<CommentDto> replies;
    private Boolean isApproved;
}
