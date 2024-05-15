package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.ConfigDto;
import com.wiinvent.gami.domain.entities.Config;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.ConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/configs")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Config", description = "Api collection")
public class ConfigController {
  @Autowired
  private ConfigService configService;

  @GetMapping("{id}")
  public Config detailConfig(@PathVariable Integer id) {
    return configService.detail(id);
  }

  @GetMapping("")
  public PageResponse getConfigs(
      @PageableDefault(size = 20)
      @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      Pageable pageable) {
    return configService.getConfigs(pageable);
  }

  @PostMapping()
  public ResponseEntity<Integer> createConfig(@RequestBody @Valid ConfigDto configDto) {
    return ResponseEntity.ok(configService.create(configDto));
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateConfig(
      @PathVariable Integer id, @RequestBody @Valid ConfigDto configDto) {
    configService.update(id, configDto);
    return ResponseEntity.ok(true);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
    return ResponseEntity.ok(configService.delete(id));
  }
}
