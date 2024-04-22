package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.BannerCreateDto;
import com.wiinvent.gami.domain.dto.BannerUpdateDto;
import com.wiinvent.gami.domain.entities.Banner;
import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.BannerResponse;
import com.wiinvent.gami.domain.utils.Constants;
import com.wiinvent.gami.domain.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Log4j2
public class BannerService extends BaseService{
  @Autowired @Lazy BannerService self;

  public Page<BannerResponse> findAll(BannerType type, Pageable pageable) {
    Page<Banner> banners = bannerStorage.findAll(type, pageable);
    return modelMapper.toPageBannerResponse(banners);
  }

  public BannerResponse getBannerDetail(Integer id){
    Banner banner = bannerStorage.findById(id);
    if(Objects.isNull(banner)) throw new ResourceNotFoundException(Constants.BANNER_NOT_FOUND);

    return modelMapper.toBannerResponse(banner);
  }

  public Boolean createBanner(BannerCreateDto dto){
    Banner banner = modelMapper.toBanner(dto);
    if(Objects.isNull(banner.getStatus())) banner.setStatus(Status.ACTIVE);
    try {
      self.save(banner);
    }catch (Exception e){
      log.error("==============>createBanner exception = {}", e.getMessage());
      return false;
    }
    return true;
  }

  public Boolean updateBanner(BannerUpdateDto dto){
    Banner banner = bannerStorage.findById(dto.getId());
    if(Objects.isNull(banner)) throw new ResourceNotFoundException(Constants.BANNER_NOT_FOUND);

    modelMapper.mapBannerUpdateDtoToBanner(dto, banner);
    banner.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    try {
      self.save(banner);
    }catch (Exception e){
      log.error("==============>updateBanner exception = {}", e.getMessage());
      return false;
    }
    return true;
  }

  public Boolean deleteBanner(Integer id){
    Banner banner = bannerStorage.findById(id);
    if(Objects.isNull(banner)) throw new ResourceNotFoundException(Constants.BANNER_NOT_FOUND);
    banner.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    banner.setStatus(Status.DELETE);
    try {
      self.save(banner);
    }catch (Exception e){
      log.error("==============>deleteBanner exception = {}", e.getMessage());
      return false;
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Banner banner){
    bannerStorage.save(banner);
  }
}