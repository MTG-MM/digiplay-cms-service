package com.wiinvent.gami.app.controller.user;

import com.wiinvent.gami.domain.dto.UserSegmentCreateDto;
import com.wiinvent.gami.domain.dto.UserSegmentUpdateDto;
import com.wiinvent.gami.domain.response.UserSegmentResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.user.UserSegmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/user-segment")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Level", description = "Level")
public class UserSegmentController {

  private final UserSegmentService userSegmentService;

  @Autowired
  public UserSegmentController(UserSegmentService userSegmentService) {
    this.userSegmentService = userSegmentService;
  }

  @GetMapping("{segmentId}")
  @Operation(summary = "Lấy chi tiết thông tin level")
  public ResponseEntity<UserSegmentResponse> getUserSegmentDetails(@PathVariable long segmentId) {
    return ResponseEntity.ok(userSegmentService.getUserSegmentDetail(segmentId));
  }

  @GetMapping("")
  @PageableAsQueryParam
  @Operation(summary = "Lấy danh sách level")
  public ResponseEntity<PageResponse<UserSegmentResponse>> findAll(@Parameter(hidden = true) Pageable pageable) {
    return ResponseEntity.ok(PageResponse.createFrom(userSegmentService.getPageUserSegment(pageable)));
  }

  @PostMapping("")
  @Operation(summary = "Tạo level")
  public ResponseEntity<Boolean> createUserSegment(@RequestBody UserSegmentCreateDto userSegmentCreateDto){
    userSegmentService.createUserSegment(userSegmentCreateDto);
    return ResponseEntity.ok(true);
  }

  @PutMapping("{segmentId}")
  @Operation(summary = "Sửa level")
  public ResponseEntity<Boolean> updateUserSegment(@PathVariable long segmentId, @RequestBody UserSegmentUpdateDto userSegmentUpdateDto){
    userSegmentService.updateUserSegment(segmentId, userSegmentUpdateDto);
    return ResponseEntity.ok(true);
  }

  @GetMapping("/active")
  @Operation(summary = "Lấy danh sách level active")
  public ResponseEntity<List<UserSegmentResponse>> findAllUserSegmentActive() {
    return ResponseEntity.ok(userSegmentService.findAllUserSegmentActive());
  }

}
