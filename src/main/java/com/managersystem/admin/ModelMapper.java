package com.managersystem.admin;


import com.managersystem.admin.handleRequest.controller.dto.*;
import com.managersystem.admin.handleRequest.controller.response.*;
import com.managersystem.admin.server.entities.*;
import com.managersystem.admin.server.pojo.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
  default <T> Page<T> mapPage(Page<T> source) {
    return source;
  }

  Account toAccountEntity(AccountDto dto);

  TokenInfo toTokenInfo(Account account);

  Account toAccountEntity(TokenInfo tokenInfo);

  User toUser(UserInfoDto userInfoDto);


  ApplicationResponse toApplicationResponse(ApplicationEntity applicationEntity);

  List<ApplicationResponse> toApplicationResponses(List<ApplicationEntity> industryGroupEntities);

  void toApplicationEntity(ApplicationDto dto, @MappingTarget ApplicationEntity entity);

  ApplicationEntity toApplicationEntity(ApplicationDto dto);


  RewardItemResponse toRewardItemResponse(RewardItem rw);

  RewardItem toRewardItem(RewardItemDto rewardItemDto);

  void mapRewardItemDtoToRewardItem(RewardItemDto rewardItemDto, @MappingTarget RewardItem rewardItem);

  RewardSegment toRewardSegment(RewardSegmentDto rewardSegmentDto);

  RewardSegmentResponse toRewardSegmentResponse(RewardSegment rewardSegment);

  void mapRewardSegmentDtoToRewardSegment(RewardSegmentDto rewardSegmentDto, @MappingTarget RewardSegment rewardSegment);


  RewardSegmentDetail toRewardSegmentDetail(RewardSegmentDetailDto rewardSegmentDetailDto);

  void mapRewardSegmentDetailDtoToRewardSegmentDetail(RewardSegmentDetailDto rewardSegmentDetailDto, @MappingTarget RewardSegmentDetail rewardSegmentDetail);

  RewardSegmentDetailResponse toRewardSegmentDetailResponse(RewardSegmentDetail rewardSegmentDetail);

  RewardScheduleResponse toRewardScheduleResponse(RewardSchedule rewardSchedule);

  void mapRewardScheduleDtoToRewardSchedule(RewardScheduleDto rewardScheduleDto, @MappingTarget RewardSchedule rewardSchedule);

  RewardSchedule toRewardSchedule(RewardScheduleDto rewardScheduleDto);


  List<RewardScheduleResponse> toListRewardScheduleResponse(List<RewardSchedule> all);

  List<RewardSegmentDetailResponse> toRewardSegmentDetailResponses(List<RewardSegmentDetail> all);

  default Page<ApplicationResponse> toPageApplicationResponse(Page<ApplicationEntity> applications) {
    return applications.map(this::toApplicationResponse);
  }

  default Page<RewardSegmentDetailResponse> toPageRewardSegmentDetailResponse(Page<RewardSegmentDetail> rewardSegmentDetails) {
    return rewardSegmentDetails.map(this::toRewardSegmentDetailResponse);
  }

  default Page<RewardSegmentResponse> toPageRewardSegmentResponse(Page<RewardSegment> rewardSegments) {
    return rewardSegments.map(this::toRewardSegmentResponse);
  }

  default Page<RewardItemResponse> toPageRewardItemResponse(Page<RewardItem> rewardItems) {
    return rewardItems.map(this::toRewardItemResponse);
  }

  void mapRewardScheduleUpdateDtoToRewardSchedule(RewardScheduleUpdateDto rewardScheduleDto,@MappingTarget RewardSchedule rewardSchedule);

  List<RewardTypeResponse> toPageRewardTypeResponse(List<RewardType> rewardItems);

  RewardType toRewardType(RewardTypeDto rewardTypeDto);

  void mapRewardTypeDtoToRewardType(RewardTypeUpdateDto rewardTypeDto,@MappingTarget RewardType rewardType);

  RewardTypeResponse toRewardTypeResponse(RewardType rewardType);

  default Page<VoucherDetailResponse> toPageVoucherDetailResponse(Page<VoucherDetail> voucherDetails){
    return voucherDetails.map(this::toVoucherDetailResponse);
  }

  VoucherDetailResponse toVoucherDetailResponse(VoucherDetail voucherDetail);

  RewardItemStoreResponse toRewardItemStoreResponse(RewardItemStore rewardItemStore);

  default Page<RewardItemStoreResponse> toPageRewardItemStoreResponse(Page<RewardItemStore> rewardItemStores){
    return rewardItemStores.map(this::toRewardItemStoreResponse);
  }

  RewardItemStore toRewardItemStore(RewardItemStoreCreateDto rewardItemStoreDto);

  void mapRewardItemStoreDtoToRewardItemStore(RewardItemStoreUpdateDto rewardItemStoreDto,@MappingTarget RewardItemStore rewardItemStore);

  List<RewardItemStoreResponse> toListRewardItemStoreResponses(List<RewardItemStore> byType);
}
