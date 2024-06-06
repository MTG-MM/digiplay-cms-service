package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.ExchangeItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.ExchangeItemStoreUpdateDto;
import com.wiinvent.gami.domain.dto.ProcessQuantityDto;
import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.reward.RewardType;
import com.wiinvent.gami.domain.entities.type.*;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.ExchangeItemStoreResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ExchangeItemStoreService extends BaseService {
  @Autowired
  @Lazy
  private ExchangeItemStoreService self;

  public ExchangeItemStoreResponse exchangeItemStoreDetail(Long id) {
    ExchangeItemStore exchangeItemStore = exchangeItemStoreStorage.findById(id);
    if (exchangeItemStore == null) {
      throw new ResourceNotFoundException("item not found");
    }
    return modelMapper.toExchangeItemStoreResponse(exchangeItemStore);
  }

  public PageResponse<ExchangeItemStoreResponse> getAllExchangeItemStores(StoreType type, String name, Pageable pageable) {
    Page<ExchangeItemStore> exchangeItemStores = exchangeItemStoreStorage.findAll(type, name, pageable);
    Page<ExchangeItemStoreResponse> responses = modelMapper.toPageExchangeItemStoreResponse(exchangeItemStores);
    return PageResponse.createFrom(responses);
  }

  public Boolean createExchangeItemStore(ExchangeItemStoreCreateDto createDto) {
    ExchangeItemStore exchangeItemStore = modelMapper.toExchangeItemStore(createDto);
    try {
      self.save(exchangeItemStore);
    } catch (Exception e) {
      log.debug("==============>createExchangeItemStores:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public Boolean updateExchangeItemStore(Long id, ExchangeItemStoreUpdateDto updateDto) {
    ExchangeItemStore exchangeItemStore = exchangeItemStoreStorage.findById(id);
    if (exchangeItemStore == null) {
      throw new ResourceNotFoundException("item not found");
    }

    modelMapper.mapExchangeItemStoreUpdateDtoToExchangeItemStore(updateDto, exchangeItemStore);
    try {
      self.save(exchangeItemStore);
    } catch (Exception e) {
      log.debug("==============>updateExchangeItemStores:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  public boolean deleteExchangeItemStore(Long id) {
    ExchangeItemStore exchangeItemStore = exchangeItemStoreStorage.findById(id);
    if (exchangeItemStore == null) {
      throw new ResourceNotFoundException("item not found");
    }
    exchangeItemStore.setStatus(Status.DELETE);
    try {
      self.save(exchangeItemStore);
    } catch (Exception e) {
      log.error("==============>deleteExchangeItemStores:DB:Exception:{}", e.getMessage());
      throw e;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean updateQuantity(ProcessQuantityDto dto) {
    ExchangeItemStore exchangeItemStore = exchangeItemStoreStorage.findById(dto.getId());
    if (exchangeItemStore == null) {
      throw new ResourceNotFoundException("Exchange item not found");
    }

    if (exchangeItemStore.getRewardItems() == null || exchangeItemStore.getRewardItems().isEmpty()) {
      throw new ResourceNotFoundException("This store does not have any reward items");
    }

    RewardItem rewardItem = rewardItemStorage.findById(exchangeItemStore.getRewardItems().getFirst().getId());
    if (rewardItem == null) {
      throw new ResourceNotFoundException("Reward item not found");
    }
    RewardType rewardType = rewardTypeStorage.findById(rewardItem.getRewardTypeId());
    if (rewardType == null) {
      throw new ResourceNotFoundException("Type not found");
    }

    if (rewardType.getType() == RewardItemType.VOUCHER) {
      handleVoucherDetails(dto, rewardItem, exchangeItemStore);
    }
    else if (rewardType.getType() == RewardItemType.PRODUCT) {
      handleProductDetails(dto, rewardItem, exchangeItemStore);
    }
    else {
      if (dto.getType().equals(ProcessQuantityDto.ProcessQuantityType.ADD)) {
        addQuantities(rewardItem, exchangeItemStore, dto.getQuantity());
      } else if (dto.getType().equals(ProcessQuantityDto.ProcessQuantityType.SUBTRACT)) {
        if (exchangeItemStore.getQuantity() < dto.getQuantity()) {
          throw new BadRequestException("Quantity need to be greater than 0");
        }
        subtractQuantities(rewardItem, exchangeItemStore, dto.getQuantity());
      }
    }
    rewardItemStorage.save(rewardItem);
    exchangeItemStoreStorage.save(exchangeItemStore);
    return true;
  }

  private void handleVoucherDetails(ProcessQuantityDto dto, RewardItem rewardItem, ExchangeItemStore exchangeItemStore) {
    List<VoucherDetail> voucherDetails = new ArrayList<>();
    if (dto.getType() == ProcessQuantityDto.ProcessQuantityType.ADD) {
      if (rewardItem.getQuantity() < dto.getQuantity()) {
        throw new BadRequestException("Quantity of Reward Item is not enough");
      }
      voucherDetails = voucherDetailStorage.findVoucherByStoreIdAndLimit(Long.valueOf(rewardItem.getExternalId()), dto.getQuantity(), RewardItemStatus.READY_TO_USE);
      voucherDetails.forEach(v -> v.setStatus(RewardItemStatus.IN_STORE));
      addQuantities(rewardItem, exchangeItemStore, voucherDetails.size());
    } else if (dto.getType() == ProcessQuantityDto.ProcessQuantityType.SUBTRACT) {
      if (exchangeItemStore.getQuantity() < dto.getQuantity()) {
        throw new BadRequestException("Quantity is not enough");
      }
      voucherDetails = voucherDetailStorage.findVoucherByStoreIdAndLimit(Long.valueOf(rewardItem.getExternalId()), dto.getQuantity(), RewardItemStatus.IN_STORE);
      voucherDetails.forEach(v -> v.setStatus(RewardItemStatus.READY_TO_USE));
      subtractQuantities(rewardItem, exchangeItemStore, voucherDetails.size());
    }
    if (voucherDetails.isEmpty()) {
      throw new BadRequestException("Voucher is not enough quantity");
    }
    voucherDetailStorage.saveAll(voucherDetails);
  }

  private void handleProductDetails(ProcessQuantityDto dto, RewardItem rewardItem, ExchangeItemStore exchangeItemStore) {
    List<ProductDetail> productDetails = new ArrayList<>();
    if (dto.getType() == ProcessQuantityDto.ProcessQuantityType.ADD) {
      if (rewardItem.getQuantity() < dto.getQuantity()) {
        throw new BadRequestException("Quantity of Reward Item is not enough");
      }
      productDetails = productDetailStorage.findProductByStoreIdAndLimit(Long.valueOf(rewardItem.getExternalId()), dto.getQuantity(), RewardItemStatus.READY_TO_USE);
      productDetails.forEach(v -> v.setStatus(RewardItemStatus.IN_STORE));
      addQuantities(rewardItem, exchangeItemStore, productDetails.size());
    } else if (dto.getType() == ProcessQuantityDto.ProcessQuantityType.SUBTRACT) {
      if (exchangeItemStore.getQuantity() < dto.getQuantity()) {
        throw new BadRequestException("Quantity is not enough");
      }
      productDetails = productDetailStorage.findProductByStoreIdAndLimit(Long.valueOf(rewardItem.getExternalId()), dto.getQuantity(), RewardItemStatus.IN_STORE);
      productDetails.forEach(v -> v.setStatus(RewardItemStatus.READY_TO_USE));
      subtractQuantities(rewardItem, exchangeItemStore, productDetails.size());
    }
    if (productDetails.isEmpty()) {
      throw new BadRequestException("Product is not enough quantity");
    }
    productDetailStorage.saveAll(productDetails);
  }

  private void addQuantities(RewardItem rewardItem, ExchangeItemStore exchangeItemStore, long quantity) {
    rewardItem.minusQuantity(quantity);
    rewardItem.setUsedQuantity(rewardItem.getUsedQuantity() + quantity);
    exchangeItemStore.addQuantity(quantity);
  }

  private void subtractQuantities(RewardItem rewardItem, ExchangeItemStore exchangeItemStore, long quantity) {
    rewardItem.addQuantity(quantity);
    rewardItem.setUsedQuantity(rewardItem.getUsedQuantity() - quantity);
    exchangeItemStore.minusQuantity(quantity);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(ExchangeItemStore exchangeItemStore) {
    exchangeItemStoreStorage.save(exchangeItemStore);
  }
}
