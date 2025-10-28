package com.devspark.antifakenews.mapper;

import com.devspark.antifakenews.dto.CommentDto;
import com.devspark.antifakenews.dto.UserDto;
import com.devspark.antifakenews.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for Comment entity and DTOs
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    /**
     * Convert Comment entity to CommentDto
     * 
     * @param comment the comment entity
     * @return comment DTO
     */
    @Mapping(target = "newsId", source = "news.id")
    @Mapping(target = "parentCommentId", source = "parentComment.id")
    CommentDto toDto(Comment comment);

    /**
     * Convert list of Comment entities to list of CommentDtos
     * 
     * @param commentList the list of comment entities
     * @return list of comment DTOs
     */
    List<CommentDto> toDtoList(List<Comment> commentList);
}
