package com.wiinvent.gami.app.controller.user.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiinvent.gami.domain.response.*;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.PackageHistoryService;
import com.wiinvent.gami.domain.service.reward.RewardItemHistoryService;
import com.wiinvent.gami.domain.service.user.UserCollectionService;
import com.wiinvent.gami.domain.service.user.transaction.*;
import com.wiinvent.gami.domain.response.base.PageCursorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("v1/portal/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserDetailController {
  @Autowired PackageHistoryService packageHistoryService;
  @Autowired CoinTransactionService coinTransactionService;
  @Autowired PointTransactionService pointTransactionService;
  @Autowired ExpHistoryService expHistoryService;
  @Autowired TurnTransactionService turnTransactionService;
  @Autowired RewardItemHistoryService rewardItemHistoryService;
  @Autowired CharacterUserTransactionService characterUserTransactionService;
  @Autowired AchievementUserService achievementUserService;
  @Autowired TicketHistoryService ticketHistoryService;
  @Autowired CollectionTransactionService collectionTransactionService;
  @Autowired UserCollectionService userCollectionService;
  @Autowired LuckyPointTransactionService luckyPointTransactionService;

  @GetMapping("sub")
  public ResponseEntity<PageCursorResponse<PackageHistoryResponse>> getPackageHistory (
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(packageHistoryService.getPackageHistory(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("/transaction/coin")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getCoinTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(coinTransactionService.getCoinTransaction(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("/transaction/point")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getPointTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(pointTransactionService.getPointTransaction(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("/transaction/exp")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getExpHistoryTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(expHistoryService.getExpHistories(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("/transaction/turn")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getTurnTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(turnTransactionService.getTurnTransaction(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("reward")
  public ResponseEntity<PageCursorResponse<RewardItemHistoryResponse>> getRewardItemHistory(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(rewardItemHistoryService.getRewardItemHistory(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("/transaction/user-character")
  public ResponseEntity<PageCursorResponse<CharacterUserTransactionResponse>> getUserCharacterTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(characterUserTransactionService.getCharacterUserTransaction(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("user-achievement")
  public ResponseEntity<PageResponse<AchievementUserResponse>> getAchievementUsers(
      @RequestParam UUID userId,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable)
  {
    return ResponseEntity.ok(PageResponse.createFrom(achievementUserService.getAchievementUsers(userId, gte ,lte, pageable)));
  }

  @GetMapping("ticket-history")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getTicketHistory(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(ticketHistoryService.getTicketHistory(userId, transId, gte, lte, next, pre, limit));
  }


  @GetMapping("transaction/collection")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getCollectionTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false, defaultValue = "20") int limit,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte
  ) {
    return ResponseEntity.ok(collectionTransactionService.getCollectionTransaction(userId, transId, gte, lte, next, pre, limit));
  }

  @GetMapping("user-collection")
  public ResponseEntity<PageResponse<UserCollectionResponse>> getUserCollection(
      @RequestParam UUID userId,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable)
  {
    return ResponseEntity.ok(PageResponse.createFrom(userCollectionService.getUserCollections(userId, gte, lte, pageable)));
  }

  @GetMapping("transaction/lucky-point")
  public ResponseEntity<PageCursorResponse<TransactionResponse>> getLuckyPointTransaction(
      @RequestParam UUID userId,
      @RequestParam(required = false) UUID transId,
      @RequestParam(required = false) Long next,
      @RequestParam(required = false) Long pre,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate gte,
      @RequestParam(required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate lte,
      @RequestParam(required = false, defaultValue = "20") int limit)
  {
    return ResponseEntity.ok((luckyPointTransactionService.getLuckyPointTransaction(userId, transId, gte, lte, next, pre, limit)));
  }
}
