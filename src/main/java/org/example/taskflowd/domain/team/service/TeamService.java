package org.example.taskflowd.domain.team.service;


import lombok.RequiredArgsConstructor;
import org.example.taskflowd.domain.team.dto.TeamCreateRequest;
import org.example.taskflowd.domain.team.dto.TeamResponse;
import org.example.taskflowd.domain.team.dto.TeamUpdateRequest;
import org.example.taskflowd.domain.team.dto.UserResponse;
import org.example.taskflowd.domain.team.entity.Team;
import org.example.taskflowd.domain.team.repository.TeamRepository;
import org.example.taskflowd.domain.teammember.entity.TeamMember;
import org.example.taskflowd.domain.teammember.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    //팀 목록 조회
    public List<TeamResponse> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(this::convertToTeamResponse)
                .collect(Collectors.toList());
    }

    // 특정 팀 조회
    public TeamResponse getTeamById(Long teamId) {
        Team team = findTeamById(teamId);
        return convertToTeamResponse(team);
    }

    // 팀 생성
    @Transactional
    public TeamResponse createTeam(TeamCreateRequest request) {
        // 팀 이름 중복 체크
        if (teamRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 사용중인 팀 이름입니다.");
        }

        Team team = new Team(request.getName(), request.getDescription());
        Team savedTeam = teamRepository.save(team);

        return convertToTeamResponse(savedTeam);
    }

    // 팀 정보 수정
    @Transactional
    public TeamResponse updateTeam(Long teamId, TeamUpdateRequest request) {
        Team team = findTeamById(teamId);

        // 이름이 변경되었고, 다른 팀에서 사용 중인 이름인지 체크
        if (!team.getName().equals(request.getName()) &&
                teamRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 사용중인 팀 이름입니다.");
        }

        team.updateTeamInfo(request.getName(), request.getDescription());
        return convertToTeamResponse(team);
    }

    // 팀 삭제
    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = findTeamById(teamId);
        team.delete(); // BaseEntity의 soft delete
    }

    // 팀 멤버 목록 조회
    public List<UserResponse> getTeamMembers(Long teamId) {
        // 팀 존재 확인
        findTeamById(teamId);

        List<TeamMember> members = teamMemberRepository.findByTeamId(teamId);
        return members.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
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
        // 임시로 TeamMember 정보로 생성
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
                .orElseThrow(() -> new IllegalArgumentException("없는 팀이다. 돌아가라"));
    }
}