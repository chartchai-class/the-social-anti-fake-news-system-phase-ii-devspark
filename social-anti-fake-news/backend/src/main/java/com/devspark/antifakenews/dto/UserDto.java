package com.devspark.antifakenews.dto;

import com.devspark.antifakenews.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for User entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private User.Role role;
}
