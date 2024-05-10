package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.ExchangeItemStoreCreateDto;
import com.wiinvent.gami.domain.dto.ExchangeItemStoreUpdateDto;
import com.wiinvent.gami.domain.dto.ProcessQuantityDto;
import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import com.wiinvent.gami.domain.entities.reward.RewardItem;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.type.StoreType;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.pojo.UserRewardItems;
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
  public Boolean changeQuantity(ProcessQuantityDto dto) {
    ExchangeItemStore exchangeItemStore = exchangeItemStoreStorage.findById(dto.getId());
    if (exchangeItemStore == null) {
      throw new ResourceNotFoundException("Exchange item not found");
    }
    if(exchangeItemStore.getRewardItems() == null || exchangeItemStore.getRewardItems().isEmpty()) {
      throw new ResourceNotFoundException("Exchange item not found");
    }

    RewardItem rewardItem = rewardItemStorage.findById(exchangeItemStore.getRewardItems().getFirst().getRewardItemId());

    if (rewardItem.getQuantity() < dto.getQuantity() - exchangeItemStore.getQuantity()) {
      throw new BadRequestException("Quantity of Reward Item is not enough");
    }

    if (dto.getType().equals(ProcessQuantityDto.ProcessQuantityType.ADD)) {
      rewardItem.minusQuantity(dto.getQuantity());
      exchangeItemStore.addQuantity(dto.getQuantity());
    } else {
      if (exchangeItemStore.getQuantity() < dto.getQuantity()) {
        throw new BadRequestException("Quantity need to be greater than 0");
      }
      rewardItem.addQuantity(dto.getQuantity());
      exchangeItemStore.minusQuantity(dto.getQuantity());
    }
    rewardItemStorage.save(rewardItem);
    exchangeItemStoreStorage.save(exchangeItemStore);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(ExchangeItemStore exchangeItemStore) {
    exchangeItemStoreStorage.save(exchangeItemStore);
  }
}
