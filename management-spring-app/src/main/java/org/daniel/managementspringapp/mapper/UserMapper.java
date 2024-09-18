package org.daniel.managementspringapp.mapper;

import org.daniel.managementspringapp.dto.NewUserDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDto userDto);
    User toUser(NewUserDto newUserDto);

    UserDto fromUser(User user);

    List<UserDto> fromUserList(List<User> users);
}
