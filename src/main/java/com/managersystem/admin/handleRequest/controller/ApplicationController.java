package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.ApplicationDto;
import com.managersystem.admin.handleRequest.controller.response.ApplicationResponse;
import com.managersystem.admin.server.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mos/cms/applications")
public class ApplicationController {

  private final ApplicationService applicationService;

  @Autowired
  public ApplicationController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @PostMapping("")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Boolean> addIndustryGroup(@RequestBody ApplicationDto dto) {
    return ResponseEntity.ok(applicationService.addIndustryGroup(dto));
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Boolean> updateIndustryGroup(@PathVariable Integer id, @RequestBody ApplicationDto dto) {
    return ResponseEntity.ok(applicationService.updateIndustryGroup(id, dto));

  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
  public ResponseEntity<Boolean> deleteIndustryGroup(@PathVariable int id) {
    return ResponseEntity.ok(applicationService.deleteIndustryGroup(id));
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
  public ResponseEntity<ApplicationResponse> getIndustryGroupById(@PathVariable int id) {
    return ResponseEntity.ok(applicationService.getIndustryGroupById(id));
  }

  @GetMapping("search/{name}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
  public ResponseEntity<List<ApplicationResponse>> searchIndustryGroupByName(@PathVariable String name) {
    return ResponseEntity.ok( applicationService.searchIndustryGroupByName(name));
  }
}

