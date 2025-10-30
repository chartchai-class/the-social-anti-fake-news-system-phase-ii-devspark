package com.devspark.antifakenews.mapper;

import com.devspark.antifakenews.dto.UserDto;
import com.devspark.antifakenews.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-30T15:00:47+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.username( user.getUsername() );
        userDto.email( user.getEmail() );
        userDto.firstName( user.getFirstName() );
        userDto.lastName( user.getLastName() );
        userDto.isActive( user.getIsActive() );
        userDto.role( user.getRole() );

        return userDto.build();
    }

    @Override
    public List<UserDto> toDtoList(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( userList.size() );
        for ( User user : userList ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
