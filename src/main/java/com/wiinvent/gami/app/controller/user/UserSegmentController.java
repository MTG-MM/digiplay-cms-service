package com.wiinvent.gami.app.controller.user;

import com.wiinvent.gami.domain.dto.UserSegmentDto;
import com.wiinvent.gami.domain.response.UserSegmentResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.user.UserSegmentService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/user-segment")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class UserSegmentController {

  private final UserSegmentService userSegmentService;

  @Autowired
  public UserSegmentController(UserSegmentService userSegmentService) {
    this.userSegmentService = userSegmentService;
  }

  @GetMapping("{segmentId}")
  public ResponseEntity<UserSegmentResponse> getUserSegmentDetails(@PathVariable long segmentId) {
    return ResponseEntity.ok(userSegmentService.getUserSegmentDetail(segmentId));
  }

  @GetMapping("")
  @PageableAsQueryParam
  public ResponseEntity<PageResponse<UserSegmentResponse>> getUserSegmentDetails(@Parameter(hidden = true) Pageable pageable) {
    return ResponseEntity.ok(PageResponse.createFrom(userSegmentService.getPageUserSegment(pageable)));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createUserSegment(@RequestBody UserSegmentDto userSegmentDto){
    userSegmentService.createUserSegment(userSegmentDto);
    return ResponseEntity.ok(true);
  }
  @PutMapping("{segmentId}")
  public ResponseEntity<Boolean> updateUserSegment(@PathVariable long segmentId, @RequestBody UserSegmentDto userSegmentDto){
    userSegmentService.updateUserSegment(segmentId, userSegmentDto);
    return ResponseEntity.ok(true);
  }

}
