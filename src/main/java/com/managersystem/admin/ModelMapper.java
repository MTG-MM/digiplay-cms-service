package com.managersystem.admin;


import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {


  AccountEntity toAccountEntity(AccountDto dto);

  UserEntity toUser(UserInfoDto userInfoDto);
}
