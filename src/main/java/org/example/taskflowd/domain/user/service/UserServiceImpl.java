package org.example.taskflowd.domain.user.service;


import org.example.taskflowd.domain.common.exception.InvalidCredentialException;
import org.example.taskflowd.domain.user.dto.request.UserUpdateRequestDto;
import org.example.taskflowd.domain.user.dto.mapper.UserMapper;
import org.example.taskflowd.domain.user.dto.request.UserSaveRequestDto;
import org.example.taskflowd.domain.user.dto.request.LoginRequestDto;
import org.example.taskflowd.domain.user.dto.response.UserResponseDto;
import org.example.taskflowd.domain.user.entity.User;
import org.example.taskflowd.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.example.taskflowd.domain.auth.jwt.JwtProvider;

import java.util.List;
import java.util.stream.Collectors;


/**
 * UserService 인터페이스 구현체
 * 사용자 관련 비즈니스 로직 처리
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    // 회원가입
    @Override
    @Transactional
    public UserResponseDto save(UserSaveRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUserName(), dto.getEmail(), encodedPassword);
        userRepository.save(user);

        return UserMapper.toResponseDto(user);
    }

    // 로그인 → JWT 발급
    @Override
    @Transactional
    public String handleLogin(LoginRequestDto dto) {
        // 1. 이메일로 사용자 조회 (Soft Delete 적용)
        User user = userRepository.findByEmailAndDeletedFalse(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialException("해당 이메일이 존재하지 않습니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("비밀번호가 일치하지 않습니다.");
        }

        // 3. JWT 생성 (일관되게 userId를 subject로)
        return jwtProvider.createToken(String.valueOf(user.getId()));
    }


    // 모든 유저 조회
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // 특정 유저 조회
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        if(user.isDeleted()) throw new IllegalArgumentException("삭제된 사용자입니다.");
        return UserMapper.toResponseDto(user);
    }


    // 유저 계정 내용 수정
    @Override
    @Transactional
    public UserResponseDto update(Long userId, UserUpdateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (userRepository.existsByEmail(dto.getEmail()) && !user.getEmail().equals(dto.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 사용중입니다.");
        }
        if (userRepository.existsByUserName(dto.getUserName()) && !user.getUserName().equals(dto.getUserName())) {
            throw new IllegalArgumentException("해당 사용자 이름은 이미 사용중입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.update(dto.getUserName(), dto.getEmail(), encodedPassword);

        return UserMapper.toResponseDto(user);
    }

    // 회원탈퇴 (Soft Delete + 비밀번호 확인)
    @Override
    @Transactional
    public void deleteById(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialException("비밀번호가 일치하지 않습니다.");
        }

        user.softDelete();
    }
}
