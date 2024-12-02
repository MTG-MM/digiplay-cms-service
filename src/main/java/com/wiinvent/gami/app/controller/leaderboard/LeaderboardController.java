package com.wiinvent.gami.app.controller.leaderboard;

import com.wiinvent.gami.domain.dto.LeaderboardCreateDto;
import com.wiinvent.gami.domain.dto.LeaderboardUpdateDto;
import com.wiinvent.gami.domain.response.LeaderboardResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.leaderboard.LeaderBoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/portal/leaderboard")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class LeaderboardController {
  @Autowired
  private LeaderBoardService leaderBoardService;
  @GetMapping("")
  public ResponseEntity<PageResponse<LeaderboardResponse>> findAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long id,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    return ResponseEntity.ok(leaderBoardService.getAllLeaderboard(id, name, pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<LeaderboardResponse> getLeaderboardDetail(@PathVariable long id) {
    return ResponseEntity.ok(leaderBoardService.getLeaderboardDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createLeaderboard(@RequestBody @Valid LeaderboardCreateDto createDto) {
    return ResponseEntity.ok(
        leaderBoardService.createLeaderboard(createDto)
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<Boolean> updateLeaderboard(@PathVariable long id, @RequestBody @Valid LeaderboardUpdateDto dto) {
    return ResponseEntity.ok(
        leaderBoardService.updateLeaderboard(id, dto)
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deleteLeaderboard(@PathVariable long id) {
    return ResponseEntity.ok(
        leaderBoardService.deleteLeaderboard(id)
    );
  }
}
