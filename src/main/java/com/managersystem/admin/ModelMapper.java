package com.managersystem.admin;


import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.ApplicationDto;
import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.handleRequest.controller.response.ApplicationResponse;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.ApplicationEntity;
import com.managersystem.admin.server.entities.UserEntity;
import com.managersystem.admin.server.pojo.TokenInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
  default <T> Page<T> mapPage(Page<T> source) {
    return source;
  }

  AccountEntity toAccountEntity(AccountDto dto);

  TokenInfo toTokenInfo(AccountEntity account);

  AccountEntity toAccountEntity(TokenInfo tokenInfo);

  UserEntity toUser(UserInfoDto userInfoDto);


  ApplicationResponse toApplicationResponse(ApplicationEntity applicationEntity);

  List<ApplicationResponse> toApplicationResponses(List<ApplicationEntity> industryGroupEntities);

  void toApplicationEntity(ApplicationDto dto, @MappingTarget ApplicationEntity entity);

  ApplicationEntity toApplicationEntity(ApplicationDto dto);

  default Page<ApplicationResponse> toPageApplicationResponse(Page<ApplicationEntity> applications) {
    return applications.map(this::toApplicationResponse);
  }

}
