package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.FeatureCreateDto;
import com.wiinvent.gami.domain.dto.FeatureUpdateDto;
import com.wiinvent.gami.domain.entities.type.FeatureCode;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.entities.user.Feature;
import com.wiinvent.gami.domain.entities.user.UserSegment;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.FeatureResponse;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class FeatureService extends BaseService{
  @Autowired @Lazy private  FeatureService self;

  public Page<FeatureResponse> findAll(String name, FeatureCode featureCode, Pageable pageable){
    Page<Feature> features = featureStorage.findAll(name, featureCode, pageable);

    return modelMapper.toPageFeatureResponse(features);
  }

  public FeatureResponse getFeatureDetail(Integer id){
    Feature feature = featureStorage.findById(id);
    if(Objects.isNull(feature)) throw new ResourceNotFoundException(Constants.FEATURE_NOT_FOUND);

    return modelMapper.toFeatureResponse(feature);
  }

  public Boolean createFeature(FeatureCreateDto dto){
    //validation
    if(Objects.isNull(dto.getStatus())) dto.setStatus(Status.ACTIVE);
    UserSegment userSegment = userSegmentStorage.findByLevel(dto.getLevelUnlock());
    if(Objects.isNull(userSegment)) throw new ResourceNotFoundException(Constants.USER_SEGMENT_NOT_FOUND);
    //map
    Feature feature = modelMapper.toFeature(dto);
    //save
    try {
      self.save(feature);
    }catch (Exception e){
      log.error("==============>createFeature:DB:Exception:{}", e.getMessage());
      throw e;
    }
    //response
    return true;
  }

  public Boolean updateFeature(FeatureUpdateDto dto){
    //validation
    Feature feature = featureStorage.findById(dto.getId());
    if(Objects.isNull(feature)) throw new ResourceNotFoundException(Constants.FEATURE_NOT_FOUND);
    if(!feature.getLevelUnlock().equals(dto.getLevelUnlock())){
      UserSegment userSegment = userSegmentStorage.findByLevel(dto.getLevelUnlock());
      if(Objects.isNull(userSegment)) throw new ResourceNotFoundException(Constants.USER_SEGMENT_NOT_FOUND);
    }
    //map
    modelMapper.mapFeatureUpdateDtoToFeature(dto, feature);
    feature.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(feature);
    }catch (Exception e){
      log.error("==============>updateFeature:DB:Exception:{}", e.getMessage());
      throw e;
    }
    //response
    return true;
  }

  public Boolean deleteFeature(Integer id){
    Feature feature = featureStorage.findById(id);
    if(Objects.isNull(feature)) throw new ResourceNotFoundException(Constants.FEATURE_NOT_FOUND);
    //map
    feature.setStatus(Status.DELETE);
    feature.setUpdatedAt(DateUtils.getNowMillisAtUtc());
    //save
    try {
      self.save(feature);
    }catch (Exception e){
      log.error("==============>deleteFeature:DB:Exception:{}", e.getMessage());
      throw e;
    }
    //response
    return true;
  }

  public List<FeatureCode> findAllFeatureCode(){
    return Arrays.stream(FeatureCode.values()).toList();
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
  public void save(Feature feature){
    featureStorage.save(feature);
  }
}