package com.devspark.antifakenews.mapper;

import com.devspark.antifakenews.dto.NewsDto;
import com.devspark.antifakenews.dto.UserDto;
import com.devspark.antifakenews.dto.CommentDto;
import com.devspark.antifakenews.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.IterableMapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for News entity and DTOs
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CommentMapper.class})
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    /**
     * Convert News entity to NewsDto
     * 
     * @param news the news entity
     * @return news DTO
     */
    @Mapping(target = "commentCount", expression = "java(news.getComments() != null ? (long) news.getComments().size() : 0L)")
    @Named("toDto")
    NewsDto toDto(News news);

    /**
     * Convert News entity to NewsDto without comments
     * 
     * @param news the news entity
     * @return news DTO without comments
     */
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "commentCount", expression = "java(news.getComments() != null ? (long) news.getComments().size() : 0L)")
    @Named("toDtoWithoutComments")
    NewsDto toDtoWithoutComments(News news);

    /**
     * Convert list of News entities to list of NewsDtos
     * 
     * @param newsList the list of news entities
     * @return list of news DTOs
     */
    @IterableMapping(qualifiedByName = "toDto")
    List<NewsDto> toDtoList(List<News> newsList);

    /**
     * Convert list of News entities to list of NewsDtos without comments
     * 
     * @param newsList the list of news entities
     * @return list of news DTOs without comments
     */
    @IterableMapping(qualifiedByName = "toDtoWithoutComments")
    List<NewsDto> toDtoListWithoutComments(List<News> newsList);

    /**
     * Convert list of News entities to list of NewsDtos with vote count
     * 
     * @param newsList the list of news entities
     * @return list of news DTOs with vote count
     */
    @IterableMapping(qualifiedByName = "toDtoWithVoteCount")
    List<NewsDto> toDtoListWithVoteCount(List<News> newsList);

    /**
     * Convert News entity to NewsDto with vote count
     * 
     * @param news the news entity
     * @return news DTO with vote count
     */
    @Mapping(target = "commentCount", expression = "java(news.getComments() != null ? (long) news.getComments().size() : 0L)")
    @Mapping(target = "voteCount", source = "voteCount", qualifiedByName = "mapVoteCount")
    @Named("toDtoWithVoteCount")
    NewsDto toDtoWithVoteCount(News news);

    /**
     * Map nested vote count value object into DTO.
     */
    @Named("mapVoteCount")
    @Mapping(target = "fakeVotes", source = "fakeVotes")
    @Mapping(target = "notFakeVotes", source = "notFakeVotes")
    @Mapping(target = "totalVotes", expression = "java(voteCount.getTotalVotes())")
    @Mapping(target = "fakePercentage", expression = "java(voteCount.getFakePercentage())")
    @Mapping(target = "notFakePercentage", expression = "java(voteCount.getNotFakePercentage())")
    NewsDto.VoteCountDto mapVoteCount(News.VoteCount voteCount);
}
