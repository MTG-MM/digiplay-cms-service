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

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {


  AccountEntity toAccountEntity(AccountDto dto);

  TokenInfo toTokenInfo(AccountEntity account);

  AccountEntity toAccountEntity(TokenInfo tokenInfo);

  UserEntity toUser(UserInfoDto userInfoDto);


  ApplicationResponse toIndustryGroupResponse(ApplicationEntity applicationEntity);

  List<ApplicationResponse> toIndustryGroupResponses(List<ApplicationEntity> industryGroupEntities);

  void toIndustryGroupEntity(ApplicationDto dto, @MappingTarget ApplicationEntity entity);

  ApplicationEntity toIndustryGroupEntity(ApplicationDto dto);
}
