package com.devspark.antifakenews.mapper;

import com.devspark.antifakenews.dto.CommentDto;
import com.devspark.antifakenews.entity.Comment;
import com.devspark.antifakenews.entity.News;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-30T15:00:47+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommentDto toDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.CommentDtoBuilder commentDto = CommentDto.builder();

        commentDto.newsId( commentNewsId( comment ) );
        commentDto.parentCommentId( commentParentCommentId( comment ) );
        commentDto.text( comment.getText() );
        commentDto.imageUrl( comment.getImageUrl() );
        commentDto.voteType( comment.getVoteType() );
        commentDto.user( userMapper.toDto( comment.getUser() ) );
        commentDto.replies( toDtoList( comment.getReplies() ) );
        commentDto.isApproved( comment.getIsApproved() );

        return commentDto.build();
    }

    @Override
    public List<CommentDto> toDtoList(List<Comment> commentList) {
        if ( commentList == null ) {
            return null;
        }

        List<CommentDto> list = new ArrayList<CommentDto>( commentList.size() );
        for ( Comment comment : commentList ) {
            list.add( toDto( comment ) );
        }

        return list;
    }

    private Long commentNewsId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        News news = comment.getNews();
        if ( news == null ) {
            return null;
        }
        Long id = news.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long commentParentCommentId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Comment parentComment = comment.getParentComment();
        if ( parentComment == null ) {
            return null;
        }
        Long id = parentComment.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
