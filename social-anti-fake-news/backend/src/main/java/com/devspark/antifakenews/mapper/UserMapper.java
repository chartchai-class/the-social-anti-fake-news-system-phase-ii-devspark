package com.devspark.antifakenews.mapper;

import com.devspark.antifakenews.dto.UserDto;
import com.devspark.antifakenews.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for User entity and DTOs
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Convert User entity to UserDto
     * 
     * @param user the user entity
     * @return user DTO
     */
    UserDto toDto(User user);

    /**
     * Convert list of User entities to list of UserDtos
     * 
     * @param userList the list of user entities
     * @return list of user DTOs
     */
    List<UserDto> toDtoList(List<User> userList);
}
