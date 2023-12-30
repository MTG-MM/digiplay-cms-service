package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.dto.IndustryGroupDto;
import com.managersystem.admin.handleRequest.controller.response.IndustryGroupResponse;
import com.managersystem.admin.server.entities.IndustryGroupEntity;
import com.managersystem.admin.server.service.IndustryGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mos/cms/industry-group")
public class IndustryGroupController {

  @Autowired
  private IndustryGroupService industryGroupService;

  @PostMapping("")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Boolean> addIndustryGroup(@RequestBody IndustryGroupDto dto) {
    return ResponseEntity.ok(industryGroupService.addIndustryGroup(dto));
  }

  @PutMapping("{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Boolean> updateIndustryGroup(@PathVariable Integer id, @RequestBody IndustryGroupDto dto) {
    return ResponseEntity.ok(industryGroupService.updateIndustryGroup(id, dto));

  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
  public ResponseEntity<Boolean> deleteIndustryGroup(@PathVariable int id) {
    return ResponseEntity.ok(industryGroupService.deleteIndustryGroup(id));
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
  public ResponseEntity<IndustryGroupResponse> getIndustryGroupById(@PathVariable int id) {
    return ResponseEntity.ok(industryGroupService.getIndustryGroupById(id));
  }

  @GetMapping("search/{name}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
  public ResponseEntity<List<IndustryGroupResponse>> searchIndustryGroupByName(@PathVariable String name) {
    return ResponseEntity.ok( industryGroupService.searchIndustryGroupByName(name));
  }
}

