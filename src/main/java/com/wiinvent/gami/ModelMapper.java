package com.wiinvent.gami;

import com.wiinvent.gami.domain.dto.*;
import com.wiinvent.gami.domain.dto.gvc.GvcPackageCreateDto;
import com.wiinvent.gami.domain.dto.gvc.GvcPackageUpdateDto;
import com.wiinvent.gami.domain.dto.payment.PaymentMethodCreateDto;
import com.wiinvent.gami.domain.entities.Character;
import com.wiinvent.gami.domain.entities.Package;
import com.wiinvent.gami.domain.entities.transaction.*;
import com.wiinvent.gami.domain.entities.game.Game;
import com.wiinvent.gami.domain.entities.game.GameCategory;
import com.wiinvent.gami.domain.entities.game.GamePackage;
import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.gvc.GvcPackage;
import com.wiinvent.gami.domain.entities.game.*;
import com.wiinvent.gami.domain.entities.payment.PaymentMethod;
import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import com.wiinvent.gami.domain.entities.reward.*;
import com.wiinvent.gami.domain.entities.user.*;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
import com.wiinvent.gami.domain.response.*;
import com.wiinvent.gami.domain.entities.*;
import com.wiinvent.gami.domain.pojo.TokenInfo;
import com.wiinvent.gami.domain.response.payment.PaymentMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

  List<RewardItemResponse> toRewardItemResponseList(List<RewardItem> rw);
  List<RewardTypeResponse> toRewardTypeResponseList(List<RewardType> rwType);

  RewardItem toRewardItem(RewardItemDto rewardItemDto);

  void mapRewardItemUpdateDtoToRewardItem(RewardItemUpdateDto rewardItemUpdateDto, @MappingTarget RewardItem rewardItem);

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

  default Page<RewardTypeResponse> toPageRewardTypeResponse(Page<RewardType> rewardTypes){
    return rewardTypes.map(this::toRewardTypeResponse);
  }
  RewardType toRewardType(RewardTypeDto rewardTypeDto);

  void mapRewardTypeDtoToRewardType(RewardTypeUpdateDto rewardTypeDto,@MappingTarget RewardType rewardType);

  RewardTypeResponse toRewardTypeResponse(RewardType rewardType);

  default Page<RwItemStoreDetailResponse> toPageVoucherDetailResponse(Page<VoucherDetail> voucherDetails){
    return voucherDetails.map(this::toVoucherDetailResponse);
  }
  default Page<RwItemStoreDetailResponse> toPageProductDetailResponse(Page<ProductDetail> productDetails){
    return productDetails.map(this::toProductDetailResponse);
  }
  RwItemStoreDetailResponse toVoucherDetailResponse(VoucherDetail voucherDetail);

  RwItemStoreDetailResponse toProductDetailResponse(ProductDetail productDetail);

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

  List<AccountResponse> toListAccountResponse(List<Account> accounts);

  GameResponse toGameResponse(Game game);

  default Page<GameResponse> toPageGameResponse(Page<Game> games) {
    return games.map(this::toGameResponse);
  }

  Game toGame(GameCreateDto createDto);

  void mapGameUpdateDtoToGame(GameUpdateDto updateDto, @MappingTarget Game game);

  GamePackageResponse toGamePackageResponse(GamePackage gamePackage);
  default Page<GamePackageResponse> toPageGamePackageResponse(Page<GamePackage> gamePackages){
    return gamePackages.map(this::toGamePackageResponse);
  }

  PackageResponse toPackageResponse(Package productPackage);
  default Page<PackageResponse> toPagePackageResponse(Page<Package> packages){
    return packages.map(this::toPackageResponse);
  }
  GamePackage toGamePackage(GamePackageCreateDto gamePackageCreateDto);

  Package toPackage(PackageCreateDto packageCreateDto);

  List<PackageResponse> toListPackageResponse(List<Package> packages);

  GameTournament toGameTournament(GameTournamentCreateDto gameTournamentCreateDto);

  void mapGameTournamentUpdateDtoToGameTournament(GameTournamentUpdateDto dto,@MappingTarget GameTournament gameTournament);

  default Page<GameTournamentResponse> toPageGameTournamentResponse(Page<GameTournament> gameTournaments){
    return gameTournaments.map(this::toGameTournamentResponse);
  }
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
  GameTournamentResponse toGameTournamentResponse(GameTournament gameTournament);
  default Page<GameCategoryResponse> toPageGameCategoryResponse(Page<GameCategory> gameCategories){
    return gameCategories.map(this::toGameCategoryResponse);
  }

  void mapPackageUpdateDtoToPackage(PackageUpdateDto dto,@MappingTarget Package productPackage);


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
  List<TaskUserResponse> toTaskUserResponse(List<TaskUser> taskUsers);
  List<UserGoldPigResponse> toUserGoldPigResponse(List<UserGoldPig> userGoldPigs);
  List<UserResponse> toListUserResponse(List<User> users);
  UserResponse toUserResponse(User user);

  GvcPackageResponse toGvcPackageResponse(GvcPackage gvcPackage);
  default Page<GvcPackageResponse> toPageGvcPackageResponse(Page<GvcPackage> gvcPackages){
    return gvcPackages.map(this::toGvcPackageResponse);
  }
  GvcPackage toGvcPackage(GvcPackageCreateDto dto);
  void mapGvcPackageUpdateDtoToGvcPackage(GvcPackageUpdateDto dto, @MappingTarget GvcPackage gvcPackage);

  Character toCharacter(CharacterCreateDto characterCreateDto);

  void mapCharacterUpdateDtoToCharacter(CharacterUpdateDto dto,@MappingTarget Character character);

  CharacterResponse toCharacterResponse(Character character);

  default Page<CharacterResponse> toPageCharacterResponse(Page<Character> characters){
    return characters.map(this::toCharacterResponse);
  }

  List<CharacterResponse> toListCharacterResponse(List<Character> characters);

  List<GameTournamentUserResponse> toGameTournamentUserResponse(List<GameTournamentUser> gameTournamentUsers);
  List<GameTournamentEventResponse> toGameTournamentEventResponse(List<GameTournamentEvent> gameTournamentEvents);

  FeatureResponse toFeatureResponse(Feature feature);
  default Page<FeatureResponse> toPageFeatureResponse(Page<Feature> features){
    return features.map(this::toFeatureResponse);
  }
  Feature toFeature(FeatureCreateDto dto);
  void mapFeatureUpdateDtoToFeature(FeatureUpdateDto dto, @MappingTarget Feature feature);

  PackageTypeResponse toPackageTypeResponse(PackageType packageType);
  default Page<PackageTypeResponse> toPagePackageTypeResponse(Page<PackageType> packageTypes){
    return packageTypes.map(this::toPackageTypeResponse);
  }
  PackageType toPackageType(PackageTypeCreateDto dto);
  void mapPackageTypeUpdateDtoToPackageType(PackageTypeUpdateDto dto, @MappingTarget PackageType packageType);

  BannerResponse toBannerResponse(Banner banner);
  default Page<BannerResponse> toPageBannerResponse(Page<Banner> banners){
    return banners.map(this::toBannerResponse);
  }
  Banner toBanner(BannerCreateDto dto);
  void mapBannerUpdateDtoToBanner(BannerUpdateDto dto, @MappingTarget Banner banner);

  List<GameTypeResponse> toListGameTypeResponse(List<GameType> gameTypes);
  List<GameCategoryResponse> toListGameCategoryResponse(List<GameCategory> gameCategories);
  List<UserSegmentResponse> toListUserSegmentResponse(List<UserSegment> userSegments);

  List<PackageTypeResponse> toListPackageTypeResponse(List<PackageType> packageTypes);
  List<CharacterUserTransactionResponse> toListCharacterUserTransactionResponse(List<CharacterUserTransaction> characterUserTransactions);

  Achievement toAchievement(AchievementCreateDto achievementCreateDto);

  void mapAchievementUpdateDtoToAchievement(AchievementUpdateDto dto,@MappingTarget Achievement achievement);
  AchievementResponse toAchievementResponse(Achievement achievement);

  default Page<AchievementResponse> toPageAchievementResponse(Page<Achievement> achievements){
    return achievements.map(this::toAchievementResponse);
  }

  List<AchievementUserResponse> toListAchievementUserResponse(List<AchievementUser> achievementUsers);


  Challenge toChallenge(ChallengeCreateDto challengeCreateDto);
  void mapChallengeUpdateDtoToChallenge(ChallengeUpdateDto challengeUpdateDto, @MappingTarget Challenge challenge);
  ChallengeResponse toChallengeResponse(Challenge challenge);

  default Page<ChallengeResponse> toPageChallengeResponse(Page<Challenge> challenges){
    return challenges.map(this::toChallengeResponse);
  }


  ChallengeDetail toChallengeDetail(ChallengeDetailCreateDto challengeDetailCreateDto);
  void mapChallengeDetailUpdateDtoToChallengeDetail(ChallengeDetailUpdateDto challengeDetailUpdateDto, @MappingTarget ChallengeDetail challengeDetail);
  ChallengeDetailResponse toChallengeDetailResponse(ChallengeDetail challengeDetail);

  default Page<ChallengeDetailResponse> toPageChallengeDetailResponse(Page<ChallengeDetail> challengeDetails){
    return challengeDetails.map(this::toChallengeDetailResponse);
  }

  Collection toCollection(CollectionCreateDto collectionCreateDto);

  void mapCollectionUpdateDtoToCollection(CollectionUpdateDto dto,@MappingTarget Collection collection);
  CollectionResponse toCollectionResponse(Collection collection);

  default Page<CollectionResponse> toPageCollectionResponse(Page<Collection> collections){
    return collections.map(this::toCollectionResponse);
  }

  List<TransactionResponse> toCollectionTransactionResponse(List<CollectionTransaction> collectionTransactions);
  List<TransactionResponse> toTicketHistoryResponse(List<TicketHistory> ticketHistories);
  List<TransactionResponse> toLuckyPointTransactionResponse(List<LuckyPointTransaction> luckyPointTransactions);

  UserCollectionResponse toUserCollectionResponse(UserCollection userCollection);

  ExchangeItemStoreResponse toExchangeItemStoreResponse(ExchangeItemStore exchangeItemStore);
  ExchangeItemStore toExchangeItemStore(ExchangeItemStoreCreateDto exchangeItemStoreCreateDto);
  void mapExchangeItemStoreUpdateDtoToExchangeItemStore(ExchangeItemStoreUpdateDto dto
      , @MappingTarget ExchangeItemStore exchangeItemStore);
  default Page<ExchangeItemStoreResponse> toPageExchangeItemStoreResponse(Page<ExchangeItemStore> exchangeItems){
    return exchangeItems.map(this::toExchangeItemStoreResponse);
  }

  List<GameResponse> toListGameResponse(List<Game> games);

  Config toConfig(ConfigDto dto);
  void mapConfigDtoToConfig(ConfigDto dto, @MappingTarget Config config);

  Quest toQuest(QuestCreateDto questCreateDto);
  void mapQuestUpdateDtoToQuest(QuestUpdateDto questUpdateDto, @MappingTarget Quest quest);
  QuestResponse toQuestResponse(Quest quest);

  default Page<QuestResponse> toPageQuestResponse(Page<Quest> quests){
    return quests.map(this::toQuestResponse);
  }

  QuestTurn toQuestTurn(QuestTurnCreateDto questTurnCreateDto);
  void mapQuestTurnUpdateDtoToQuestTurn(QuestTurnUpdateDto questTurnUpdateDto, @MappingTarget QuestTurn questTurn);
  QuestTurnResponse toQuestTurnResponse(QuestTurn questTurn);

  default Page<QuestTurnResponse> toPageQuestTurnResponse(Page<QuestTurn> questTurns){
    return questTurns.map(this::toQuestTurnResponse);
  }

  Task toTask(TaskCreateDto taskCreateDto);
  void mapTaskUpdateDtoToTask(TaskUpdateDto taskUpdateDto, @MappingTarget Task task);
  TaskResponse toTaskResponse(Task task);

  default Page<TaskResponse> toPageTaskResponse(Page<Task> tasks){
    return tasks.map(this::toTaskResponse);
  }

  List<AchievementResponse> toListAchievementResponse(List<Achievement> achievements);

  List<QuestUserHistoryResponse> toListQuestUserHistoryResponse(List<QuestUserHistory> questUserHistories);

  List<QuestResponse> toListQuestResponse(List<Quest> quests);

  UserRewardItems toUserRewardItems(RewardItemSelect rewardItemSelect);

  RewardItemSelect toUserRewardItemSelect(UserRewardItems rewardItemSelect);

  List<UserRewardItems> toListUserRewardItems(List<RewardItemSelect> rewardItemSelectList);

  List<RewardItemSelect> toListUserRewardItemSelect(List<UserRewardItems> rewardItemSelectList);

  void mapToRewardSchedule(RewardScheduleUpdateDto rs, @MappingTarget RewardSchedule rewardScheduleEntity);

  RewardSchedule toRewardSchedule(RewardScheduleUpdateDto rs);
}
