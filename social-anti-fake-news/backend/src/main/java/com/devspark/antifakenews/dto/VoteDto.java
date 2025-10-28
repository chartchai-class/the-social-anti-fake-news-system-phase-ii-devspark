package com.devspark.antifakenews.dto;

import com.devspark.antifakenews.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for Vote entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VoteDto extends BaseDto {
    private Vote.VoteType voteType;
    private UserDto user;
    private Long newsId;
}
