package org.example.taskflowd.domain.teammember.service;

import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.team.dto.TeamMemberAddRequest;
import org.example.taskflowd.domain.team.dto.TeamResponse;
import org.example.taskflowd.domain.team.dto.UserResponse;
import org.example.taskflowd.domain.team.entity.Team;
import org.example.taskflowd.domain.team.repository.TeamRepository;
import org.example.taskflowd.domain.teammember.entity.TeamMember;
import org.example.taskflowd.common.exception.GlobalException;
import org.example.taskflowd.domain.teammember.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamMemberService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    // 팀 멤버 추가
    @Transactional
    public TeamResponse addMember(Long teamId, TeamMemberAddRequest request) {
        Team team = findTeamById(teamId);

        // TODO: User 존재 확인 (User 도메인과 협업 필요)
        // userService.existsById(request.getUserId())

        if (teamMemberRepository.existsByTeamIdAndUserId(teamId, request.getUserId())) {
            throw new IllegalArgumentException("이미 팀에 소속되어 있습니다.");
        }

        TeamMember teamMember = new TeamMember(team, request.getUserId(), "MEMBER"); // 기본 역할
        teamMemberRepository.save(teamMember);

        return convertToTeamResponse(team);
    }

    @Transactional
    public TeamResponse removeMember(Long teamId, Long userId) {
        Team team = findTeamById(teamId);

        // 팀 멤버 존재 확인
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new IllegalArgumentException("그런 사람 없습니다."));

        teamMember.delete();
        return convertToTeamResponse(team);
    }

    // 추가 가능한 사용자 목록 조회
    public List<UserResponse> getAvailableUsers(Long teamId) {
        findTeamById(teamId);
        // TODO: User 도메인과 협업하여 구현 필요
        // 1. 모든 사용자 조회
        // 2. 현재 팀에 속하지 않은 사용자 필터링
        // List<User> allUsers = userService.getAllUsers();
        // List<Long> teamMemberIds = teamMemberRepository.findByTeamId(teamId)
        //     .stream().map(TeamMember::getUserId).toList();
        // return allUsers.stream()
        //     .filter(user -> !teamMemberIds.contains(user.getId()))
        //     .map(this::convertToUserResponse)
        //     .collect(Collectors.toList());

        // 임시 구현 (빈 리스트 반환)
        return List.of();
    }

    // Entity to DTO 변환 메서드
    private TeamResponse convertToTeamResponse(Team team) {
        List<UserResponse> members = teamMemberRepository.findByTeamId(team.getId())
                .stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                members
        );
    }

    private UserResponse convertToUserResponse(TeamMember teamMember) {
        // TODO: User 정보 조회 로직 필요 (User 도메인과 협업)
        return new UserResponse(
                teamMember.getUserId(),
                "username", // User 정보에서 가져와야 함
                "name",     // User 정보에서 가져와야 함
                "email",    // User 정보에서 가져와야 함
                teamMember.getRole(),
                teamMember.getCreatedAt()
        );
    }

    // 공통 메서드
    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 접근"));
    }
}
