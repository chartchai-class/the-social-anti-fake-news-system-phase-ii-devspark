package com.devspark.antifakenews.mapper;

import com.devspark.antifakenews.dto.NewsDto;
import com.devspark.antifakenews.entity.News;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-30T22:59:05+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public NewsDto toDto(News news) {
        if ( news == null ) {
            return null;
        }

        NewsDto.NewsDtoBuilder newsDto = NewsDto.builder();

        newsDto.comments( commentMapper.toDtoList( news.getComments() ) );
        newsDto.fullDetail( news.getFullDetail() );
        newsDto.imageUrl( news.getImageUrl() );
        newsDto.reporter( userMapper.toDto( news.getReporter() ) );
        newsDto.shortDetail( news.getShortDetail() );
        newsDto.status( news.getStatus() );
        newsDto.title( news.getTitle() );
        newsDto.voteCount( voteCountToVoteCountDto( news.getVoteCount() ) );

        newsDto.commentCount( news.getComments() != null ? (long) news.getComments().size() : 0L );

        return newsDto.build();
    }

    @Override
    public NewsDto toDtoWithoutComments(News news) {
        if ( news == null ) {
            return null;
        }

        NewsDto.NewsDtoBuilder newsDto = NewsDto.builder();

        newsDto.fullDetail( news.getFullDetail() );
        newsDto.imageUrl( news.getImageUrl() );
        newsDto.reporter( userMapper.toDto( news.getReporter() ) );
        newsDto.shortDetail( news.getShortDetail() );
        newsDto.status( news.getStatus() );
        newsDto.title( news.getTitle() );
        newsDto.voteCount( voteCountToVoteCountDto( news.getVoteCount() ) );

        newsDto.commentCount( news.getComments() != null ? (long) news.getComments().size() : 0L );

        return newsDto.build();
    }

    @Override
    public List<NewsDto> toDtoList(List<News> newsList) {
        if ( newsList == null ) {
            return null;
        }

        List<NewsDto> list = new ArrayList<NewsDto>( newsList.size() );
        for ( News news : newsList ) {
            list.add( toDto( news ) );
        }

        return list;
    }

    @Override
    public List<NewsDto> toDtoListWithoutComments(List<News> newsList) {
        if ( newsList == null ) {
            return null;
        }

        List<NewsDto> list = new ArrayList<NewsDto>( newsList.size() );
        for ( News news : newsList ) {
            list.add( toDtoWithoutComments( news ) );
        }

        return list;
    }

    @Override
    public List<NewsDto> toDtoListWithVoteCount(List<News> newsList) {
        if ( newsList == null ) {
            return null;
        }

        List<NewsDto> list = new ArrayList<NewsDto>( newsList.size() );
        for ( News news : newsList ) {
            list.add( toDtoWithVoteCount( news ) );
        }

        return list;
    }

    @Override
    public NewsDto toDtoWithVoteCount(News news) {
        if ( news == null ) {
            return null;
        }

        NewsDto.NewsDtoBuilder newsDto = NewsDto.builder();

        newsDto.voteCount( mapVoteCount( news.getVoteCount() ) );
        newsDto.comments( commentMapper.toDtoList( news.getComments() ) );
        newsDto.fullDetail( news.getFullDetail() );
        newsDto.imageUrl( news.getImageUrl() );
        newsDto.reporter( userMapper.toDto( news.getReporter() ) );
        newsDto.shortDetail( news.getShortDetail() );
        newsDto.status( news.getStatus() );
        newsDto.title( news.getTitle() );

        newsDto.commentCount( news.getComments() != null ? (long) news.getComments().size() : 0L );

        return newsDto.build();
    }

    @Override
    public NewsDto.VoteCountDto mapVoteCount(News.VoteCount voteCount) {
        if ( voteCount == null ) {
            return null;
        }

        NewsDto.VoteCountDto.VoteCountDtoBuilder voteCountDto = NewsDto.VoteCountDto.builder();

        voteCountDto.fakeVotes( voteCount.getFakeVotes() );
        voteCountDto.notFakeVotes( voteCount.getNotFakeVotes() );

        voteCountDto.totalVotes( voteCount.getTotalVotes() );
        voteCountDto.fakePercentage( voteCount.getFakePercentage() );
        voteCountDto.notFakePercentage( voteCount.getNotFakePercentage() );

        return voteCountDto.build();
    }

    protected NewsDto.VoteCountDto voteCountToVoteCountDto(News.VoteCount voteCount) {
        if ( voteCount == null ) {
            return null;
        }

        NewsDto.VoteCountDto.VoteCountDtoBuilder voteCountDto = NewsDto.VoteCountDto.builder();

        voteCountDto.fakePercentage( voteCount.getFakePercentage() );
        voteCountDto.fakeVotes( voteCount.getFakeVotes() );
        voteCountDto.notFakePercentage( voteCount.getNotFakePercentage() );
        voteCountDto.notFakeVotes( voteCount.getNotFakeVotes() );
        voteCountDto.totalVotes( voteCount.getTotalVotes() );

        return voteCountDto.build();
    }
}
