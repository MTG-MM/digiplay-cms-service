package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.ConfigDto;
import com.wiinvent.gami.domain.entities.Config;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.response.base.PageResponse;
import jakarta.persistence.RollbackException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Log4j2
public class ConfigService extends BaseService{
  public Integer create(ConfigDto configDto) {

    if (configStorage.existsByKey(configDto.getKey().trim())) {
      throw new BadRequestException("Config key " + configDto.getKey() + " exist!");
    }
    Config config = modelMapper.toConfig(configDto);
    try {
      configStorage.save(config);
    } catch (Exception e) {
      log.error("==> Rollback: createConfig" + e);
      throw new RollbackException(e.getMessage());
    }
    return config.getId();
  }

  public Config detail(Integer id) {
    Config config = configStorage.findById(id);
    if (config == null) {
      throw new ResourceNotFoundException("Config id: " + id + " not found");
    }
    return config;
  }

  public PageResponse getConfigs(Pageable pageable) {
    return PageResponse.createFrom(configStorage.findConfigs(pageable));
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean update(Integer id, ConfigDto configDto) throws IOException {
    Config config = configStorage.findById(id);
    if (config == null) {
      throw new ResourceNotFoundException("Config id " + id + " not found");
    }
    modelMapper.mapConfigDtoToConfig(configDto, config);
    try {
      configStorage.save(config);
    } catch (Exception e) {
      log.error("==> Rollback: updateConfig" + e);
      throw new RollbackException(e.getMessage());
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public Boolean delete(Integer id) {
    Config config = detail(id);
    if (config == null) {
      throw new ResourceNotFoundException("Config id: " + id + " not found");
    }
    try {
      configStorage.delete(config);
    } catch (Exception e) {
      log.error("==> Rollback: deleteConfig" + e);
      throw new RollbackException(e.getMessage());
    }
    return true;
  }
}
