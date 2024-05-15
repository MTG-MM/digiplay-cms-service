package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.ExchangeItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.ExchangeItemStoreUpdateDto;
import com.wiinvent.gami.domain.dto.ProcessQuantityDto;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.response.ExchangeItemStoreResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.ExchangeItemStoreService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/exchange-item-stores")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class ExchangeItemStoreController {
  @Autowired
  private ExchangeItemStoreService exchangeItemStoreService;

  @GetMapping("")
  @PageableAsQueryParam
  public ResponseEntity<PageResponse<ExchangeItemStoreResponse>> getRewardItemStores(
      @RequestParam(required = false) StoreType type,
      @RequestParam(required = false) String name,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @Parameter(hidden = true)
      Pageable pageable) {
    return ResponseEntity.ok(exchangeItemStoreService.getAllExchangeItemStores(type, name, pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<ExchangeItemStoreResponse> getRewardItemStoreDetail(@PathVariable Long id) {
    return ResponseEntity.ok(exchangeItemStoreService.exchangeItemStoreDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createRewardItemStores(@RequestBody @Valid ExchangeItemStoreCreateDto createDto) {
    return ResponseEntity.ok(exchangeItemStoreService.createExchangeItemStore(createDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateRewardItemStores(@PathVariable Long id, @RequestBody @Valid  ExchangeItemStoreUpdateDto updateDto) {
    return ResponseEntity.ok(exchangeItemStoreService.updateExchangeItemStore(id, updateDto));
  }

  @PutMapping("{changeQuantity}")
  public ResponseEntity<Boolean> changeQuantity(@RequestBody @Valid ProcessQuantityDto dto) {
    return ResponseEntity.ok(exchangeItemStoreService.changeQuantity(dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteRewardItemStores(@PathVariable Long id) {
    return ResponseEntity.ok(exchangeItemStoreService.deleteExchangeItemStore(id));
  }
}
