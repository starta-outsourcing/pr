package org.example.taskflowd.domain.user.controller;

import org.example.taskflowd.common.dto.response.ApiResponse;
import org.example.taskflowd.domain.user.dto.request.UserDeleteRequestDto;
import org.example.taskflowd.domain.user.dto.request.UserSaveRequestDto;
import org.example.taskflowd.domain.user.dto.request.UserUpdateRequestDto;
import org.example.taskflowd.domain.user.dto.response.UserResponseDto;
import org.example.taskflowd.domain.user.service.UserService;
import org.example.taskflowd.domain.user.util.UserConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 관련 CRUD API를 처리하는 컨트롤러
 * - 회원가입, 회원 조회, 회원 정보 수정, 회원 탈퇴
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService; // 실제 비즈니스 로직 처리는 UserService 에서 진행.

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(@RequestBody UserSaveRequestDto dto) {
        UserResponseDto userDto = userService.save(dto);
        return ResponseEntity.ok(ApiResponse.ofSuccess(UserConst.SIGNUP_SUCCESS, userDto));
    }

    // 전체 사용자 조회 API
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // 단일 사용자 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userDto = userService.getProfile(id);
        return ResponseEntity.ok(userDto);
    }

    //로그인한 사용자 정보 수정 API
    @PutMapping("/me/update")
    public ResponseEntity<UserResponseDto> updateAccount(
            @AuthenticationPrincipal User principal,
            @RequestBody UserUpdateRequestDto dto) {

        Long userId = Long.parseLong(principal.getUsername());
        UserResponseDto updatedUser = userService.update(userId, dto);
        return ResponseEntity.ok(updatedUser);
    }


    //로그인한 사용자 계정 삭제 API
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal User principal, @RequestBody UserDeleteRequestDto dto) {
        Long userId = Long.parseLong(principal.getUsername());
        userService.deleteById(userId, dto.getPassword());
        return ResponseEntity.ok("계정이 삭제되었습니다");
    }
        }
