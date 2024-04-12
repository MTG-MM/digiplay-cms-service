package com.wiinvent.gami.domain.stores.user;

import com.wiinvent.gami.domain.entities.user.Feature;
import com.wiinvent.gami.domain.stores.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FeatureStorage extends BaseStorage {
  public Page<Feature> findAll(Pageable pageable){
    return featureRepository.findAll(pageable);
  }

  public Feature findById(Integer id){
    return featureRepository.findById(id).orElse(null);
  }

  public Feature save(Feature feature){
    return featureRepository.save(feature);
  }
}