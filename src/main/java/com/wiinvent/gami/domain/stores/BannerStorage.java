package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Banner;
import com.wiinvent.gami.domain.entities.type.BannerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BannerStorage extends BaseStorage{
  public Page<Banner> findAll(BannerType type, Pageable pageable) {
    return bannerRepository.findBannersByTypeAndStatusIn(type, Banner.getListStatusShow(), pageable);
  }

  public Banner findById(Integer id) {
    return bannerRepository.findBannerByIdAndStatusIn(id, Banner.getListStatusShow());
  }

  public void save(Banner banner) {
    bannerRepository.save(banner);
    remoteCache.del(genCacheKeys(banner));
  }

  public List<String> genCacheKeys(Banner banner){
    List<String> keys = new ArrayList<>();
    keys.add(cacheKey.genKeyListBannerByBannerType(banner.getType()));
    return keys;
  }
}