package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.CollectionCreateDto;
import com.wiinvent.gami.domain.dto.CollectionUpdateDto;
import com.wiinvent.gami.domain.entities.type.CollectionType;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.CollectionResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.CollectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/portal/collection")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@Tag(name = "Collection", description = "Api collection")
public class CollectionController {
  @Autowired
  private CollectionService collectionService;

  @GetMapping("")
  public ResponseEntity<PageResponse<CollectionResponse>> findAll(
      @RequestParam(required = false) CollectionType type,
      @RequestParam(required = false) Status status,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(
        PageResponse.createFrom(collectionService.getCollection(type, status, pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<CollectionResponse> getCollectionDetail(@PathVariable long id) {
    return ResponseEntity.ok(collectionService.getCollectionDetail(id));
  }

  @GetMapping("type/collect")
  private ResponseEntity<List<CollectionResponse>> findCollectionInTypeCollection(){
    return ResponseEntity.ok(collectionService.getCollectionsInTypeCollection());
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createCollection(@RequestBody CollectionCreateDto collectionCreateDto) {
    return ResponseEntity.ok(
        collectionService.createCollection(collectionCreateDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateCollection(@PathVariable Long id, @RequestBody CollectionUpdateDto dto) {
    return ResponseEntity.ok(
        collectionService.updateCollection(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteCollection(@PathVariable Long id) {
    return ResponseEntity.ok(
        collectionService.deleteCollection(id)
    );
  }
}
