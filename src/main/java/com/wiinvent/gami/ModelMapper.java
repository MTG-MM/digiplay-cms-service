package com.wiinvent.gami;

import com.wiinvent.gami.domain.dto.*;
import com.wiinvent.gami.domain.dto.payment.PaymentMethodCreateDto;
import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.Transaction.CoinTransaction;
import com.wiinvent.gami.domain.entities.Transaction.ExpHistory;
import com.wiinvent.gami.domain.entities.Transaction.PointTransaction;
import com.wiinvent.gami.domain.entities.Transaction.TurnTransaction;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.payment.PaymentMethod;
import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import com.wiinvent.gami.domain.entities.reward.*;
import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.response.*;
import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.pojo.TokenInfo;
import com.wiinvent.gami.domain.response.payment.PaymentMethodResponse;
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

  AccountResponse toAccountResponse(Account account);

  default Page<AccountResponse> toPageAccountResponse(Page<Account> accounts) {
    return accounts.map(this::toAccountResponse);
  }

  GameResponse toGameResponse(Game game);

  default Page<GameResponse> toPageGameResponse(Page<Game> games) {
    return games.map(this::toGameResponse);
  }

  Game toGame(GameCreateDto createDto);

  void mapGameUpdateDtoToGame(GameUpdateDto updateDto, @MappingTarget Game game);

  GamePackageResponse toGamePackageResponse(GamePackage gamePackage);

  PackageResponse toPackageResponse(Package aPackage);

  GamePackage toGamePackage(GamePackageCreateDto gamePackageCreateDto);

  Package toPackage(PackageCreateDto packageCreateDto);

  void mapGamePackageUpdateDtoToGamePackage(GamePackageUpdateDto dto,@MappingTarget GamePackage gamePackage);


  GameType toGameType(GameTypeCreateDto dto);
  GameTypeResponse toGameTypeResponse(GameType gameType);
  default Page<GameTypeResponse> toPageGameTypeResponse(Page<GameType> gameTypes) {
    return gameTypes.map(this::toGameTypeResponse);
  }

  PaymentMethod toPaymentMethod(PaymentMethodCreateDto dto);
  PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
  default Page<PaymentMethodResponse> toPagePaymentMethodResponse(Page<PaymentMethod> paymentMethods){
    return paymentMethods.map(this::toPaymentMethodResponse);
  }

  GameCategory toGameCategory(GameCategoryCreateDto dto);
  GameCategoryResponse toGameCategoryResponse(GameCategory gameCategory);
  default Page<GameCategoryResponse> toPageGameCategoryResponse(Page<GameCategory> gameCategories){
    return gameCategories.map(this::toGameCategoryResponse);
  }

  void mapPackageUpdateDtoToPackage(PackageUpdateDto dto,@MappingTarget Package aPackage);

  UserSegmentResponse toUserSegmentResponse(UserSegment userSegment);

  default Page<UserSegmentResponse> toPageUserSegmentResponse(Page<UserSegment> userSegments){
    return userSegments.map(this::toUserSegmentResponse);
  }

  UserSegment toUserSegment(UserSegmentCreateDto userSegmentCreateDto);

  void mapUserSegmentDtoToUserSegment(UserSegmentUpdateDto userSegmentUpdateDto, @MappingTarget UserSegment userSegment);

  List<RewardItemHistoryResponse> toListRewardItemHistoryResponse(List<RewardItemHistory> rewardItemHistories);
  List<PackageHistoryResponse> toPackageHistoryResponse(List<PackageHistory> packageHistories);

  List<TransactionResponse> toCoinTransactionResponse(List<CoinTransaction> coinTransactions);
  List<TransactionResponse> toPointTransactionResponse(List<PointTransaction> pointTransactions);
  List<TransactionResponse> toExpHistoryResponse(List<ExpHistory> expHistories);
  List<TransactionResponse> toTurnTransactionResponse(List<TurnTransaction> turnTransactions);
  List<UserResponse> toListUserResponse(List<User> users);
  UserResponse toUserResponse(User user);
}
