package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Config;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class ConfigStorage extends BaseStorage {
  public void save(Config config) {
    configRepository.save(config);
    remoteCache.put(cacheKey.genConfigKey(config.getKey()), config, Integer.MAX_VALUE);
  }
  public Config findById(Integer id) {
    return configRepository.findById(id).orElse(null);
  }
  public Page<Config> findConfigs(Pageable pageable) {
    return configRepository.findAll(pageable);
  }

  public void delete(Config config) {
    remoteCache.del(cacheKey.genConfigKey(config.getKey()));
    configRepository.delete(config);
  }

  public Boolean existsByKey(String key) {
    return configRepository.existsByKey(key);
  }

}
