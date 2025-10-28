package com.devspark.antifakenews.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Base DTO with common fields
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
public abstract class BaseDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
