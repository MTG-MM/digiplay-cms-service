package com.managersystem.admin;


import com.managersystem.admin.handleRequest.controller.dto.AccountDto;
import com.managersystem.admin.handleRequest.controller.dto.IndustryGroupDto;
import com.managersystem.admin.handleRequest.controller.dto.UserInfoDto;
import com.managersystem.admin.handleRequest.controller.response.IndustryGroupResponse;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.entities.IndustryGroupEntity;
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


  IndustryGroupResponse toIndustryGroupResponse(IndustryGroupEntity industryGroupEntity);

  List<IndustryGroupResponse> toIndustryGroupResponses(List<IndustryGroupEntity> industryGroupEntities);

  void toIndustryGroupEntity(IndustryGroupDto dto, @MappingTarget IndustryGroupEntity entity);

  IndustryGroupEntity toIndustryGroupEntity(IndustryGroupDto dto);
}
