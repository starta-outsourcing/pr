package org.example.taskflowd.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 응답 데이터를 표준화하기 위한 DTO 클래스
 * 메시지도 예의바르게 꼬박꼬박 출력하고 싶어서 만들었습니다.
 */
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
}