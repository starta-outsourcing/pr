package org.example.taskflowd.domain.team.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.team.dto.TeamCreateRequest;
import org.example.taskflowd.domain.team.dto.TeamUpdateRequest;
import org.example.taskflowd.domain.team.dto.TeamMemberAddRequest;
import org.example.taskflowd.domain.team.dto.TeamResponse;
import org.example.taskflowd.domain.team.dto.UserResponse;
import org.example.taskflowd.domain.team.service.TeamService;
import org.example.taskflowd.common.dto.response.ApiResponse;
import org.example.taskflowd.domain.teammember.service.TeamMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;

    // 팀 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamResponse>>> getAllTeams() {
        List<TeamResponse> teams = teamService.getAllTeams();
        return ApiResponse.ok(teams);
    }

    // 특정 팀 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<TeamResponse>> getTeamById(@PathVariable Long teamId) {
        TeamResponse team = teamService.getTeamById(teamId);
        return ApiResponse.ok(team);
    }

    // 팀 멤버 목록 조회
    @GetMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getTeamMembers(@PathVariable Long teamId) {
        List<UserResponse> members = teamService.getTeamMembers(teamId);
        return ApiResponse.ok(members);
    }

    // 팀 생성
    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(@Valid @RequestBody TeamCreateRequest request) {
        TeamResponse team = teamService.createTeam(request);
        return ResponseEntity.status(201).body(ApiResponse.ofSuccess(team));
    }

    // 팀 정보 수정
    @PutMapping("/{teamId}")
    public ResponseEntity<ApiResponse<TeamResponse>> updateTeam(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamUpdateRequest request) {
        TeamResponse team = teamService.updateTeam(teamId, request);
        return ApiResponse.ok(team);
    }

    // 팀 삭제
    @DeleteMapping("/{teamId}")
    public ResponseEntity<ApiResponse<Void>> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok(ApiResponse.ofSuccess("팀이 성공적으로 삭제되었습니다.", null));
    }

    // 팀 멤버 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<TeamResponse>> addMember(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamMemberAddRequest request) {
        TeamResponse team = teamMemberService.addMember(teamId, request);
        return ApiResponse.ok(team);
    }

    // 팀 멤버 제거
    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<ApiResponse<TeamResponse>> removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId) {
        TeamResponse team = teamMemberService.removeMember(teamId, userId);
        return ApiResponse.ok(team);
    }

    // 추가 가능한 사용자 목록 조회
    @GetMapping("/users/available")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAvailableUsers(@RequestParam Long teamId) {
        List<UserResponse> availableUsers = teamMemberService.getAvailableUsers(teamId);
        return ApiResponse.ok(availableUsers);
    }
}