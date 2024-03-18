package com.wiinvent.gami;

import com.wiinvent.gami.app.controller.dto.*;
import com.wiinvent.gami.app.controller.response.*;
import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.pojo.TokenInfo;
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


  ApplicationResponse toApplicationResponse(Application application);

  List<ApplicationResponse> toApplicationResponses(List<Application> industryGroupEntities);

  void toApplication(ApplicationDto dto, @MappingTarget Application entity);

  Application toApplication(ApplicationDto dto);


  RewardItemResponse toRewardItemResponse(RewardItem rw);

  RewardItem toRewardItem(RewardItemDto rewardItemDto);

  void mapRewardItemDtoToRewardItem(RewardItemDto rewardItemDto, @MappingTarget RewardItem rewardItem);

  RewardSegment toRewardSegment(RewardSegmentDto rewardSegmentDto);

  RewardSegmentResponse toRewardSegmentResponse(RewardSegment rewardSegment);

  void mapRewardSegmentDtoToRewardSegment(RewardSegmentDto rewardSegmentDto, @MappingTarget RewardSegment rewardSegment);


  RewardSegmentDetail toRewardSegmentDetail(RewardSegmentDetailDto rewardSegmentDetailDto);

  RewardSegmentDetail toRewardSegmentDetail(RewardSegmentDetailsUpdateDto createDto);

  void mapRewardSegmentDetailDtoToRewardSegmentDetail(RewardSegmentDetailUpdateDto rewardSegmentDetailDto, @MappingTarget RewardSegmentDetail rewardSegmentDetail);

  RewardSegmentDetailResponse toRewardSegmentDetailResponse(RewardSegmentDetail rewardSegmentDetail);

  RewardScheduleResponse toRewardScheduleResponse(RewardSchedule rewardSchedule);

  void mapRewardScheduleDtoToRewardSchedule(RewardScheduleDto rewardScheduleDto, @MappingTarget RewardSchedule rewardSchedule);

  RewardSchedule toRewardSchedule(RewardScheduleDto rewardScheduleDto);


  List<RewardScheduleResponse> toListRewardScheduleResponse(List<RewardSchedule> all);

  List<RewardSegmentDetailResponse> toRewardSegmentDetailResponses(List<RewardSegmentDetail> all);

  default Page<ApplicationResponse> toPageApplicationResponse(Page<Application> applications) {
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

  void mapRewardSegmentDetailsDtoToRewardSegmentDetail(RewardSegmentDetailsUpdateDto detailsUpdateDto,@MappingTarget RewardSegmentDetail rewardSegmentDetail);


}
