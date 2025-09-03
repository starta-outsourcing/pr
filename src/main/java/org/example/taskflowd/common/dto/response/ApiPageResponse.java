package org.example.taskflowd.common.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class ApiPageResponse<T> {

    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final List<T> data;

    private ApiPageResponse(int page, int size, int totalPages, long totalElements, List<T> data) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
    }

    /**
     * 성공적인 요청에 대한 페이징 응답을 반환하는 메서드
     * 주어진 데이터를 포함하여 HTTP 200 OK 상태 코드와 함께 응답을 반환
     *
     * @param pagedData 요청 성공 시 반환할 페이징 데이터
     * @return HTTP 200 OK 응답과 함께 성공 데이터가 포함된 ApiPageResponse
     */
    public static <T> ResponseEntity<ApiPageResponse<T>> success(Page<T> pagedData) {
        return ResponseEntity.ok(fromPage(pagedData));
    }

    private static <T> ApiPageResponse<T> fromPage(Page<T> pagedData) {
        return new ApiPageResponse<>(
                pagedData.getPageable().getPageNumber(),
                pagedData.getPageable().getPageSize(),
                pagedData.getTotalPages(),
                pagedData.getTotalElements(),
                pagedData.getContent()
        );
    }
}