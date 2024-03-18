package com.wiinvent.gami.app.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

//  private final ApplicationService applicationService;
//
//  @Autowired
//  public ApplicationController(ApplicationService applicationService) {
//    this.applicationService = applicationService;
//  }
//
//  @PostMapping("")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ResponseEntity<Boolean> addIndustryGroup(Authentication authentication,  @RequestBody ApplicationDto dto) {
//    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//    return ResponseEntity.ok(applicationService.addIndustryGroup(userDetails.getUsername(), dto));
//  }
//
//  @PutMapping("{id}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ResponseEntity<Boolean> updateIndustryGroup(@PathVariable Integer id, @RequestBody ApplicationDto dto) {
//    return ResponseEntity.ok(applicationService.updateIndustryGroup(id, dto));
//
//  }
//
//  @DeleteMapping("{id}")
//  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
//  public ResponseEntity<Boolean> deleteIndustryGroup(@PathVariable int id) {
//    return ResponseEntity.ok(applicationService.deleteIndustryGroup(id));
//  }
//
//  @GetMapping("{id}")
//  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
//  public ResponseEntity<ApplicationResponse> getIndustryGroupById(@PathVariable int id) {
//    return ResponseEntity.ok(applicationService.getIndustryGroupById(id));
//  }
//
//  @GetMapping()
//  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
//  public ResponseEntity<PageResponse<ApplicationResponse>> getPageApplication(@PageableDefault(page = 20) Pageable pageable) {
//    return ResponseEntity.ok(applicationService.getPageApplication(pageable));
//  }
//
//  @GetMapping("search/{name}")
//  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
//  public ResponseEntity<List<ApplicationResponse>> searchIndustryGroupByName(@PathVariable String name, @PageableDefault(page = 20) Pageable pageable) {
//    return ResponseEntity.ok( applicationService.searchApplicationByName(name, pageable));
//  }
}

