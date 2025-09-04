package org.example.taskflowd.common.enums;

import lombok.Getter;

// 각자 Message를 명세서에 따라 등록하시면 됩니다!
@Getter
public enum ResponseMessage {

    //TEAM/MEMBER
    TEAM_LIST_INQUIRE("팀 목록을 조회했습니다."),
    TEAM_OBJECT_INQUIRE("팀 정보를 조회했습니다."),
    TEAM_CREATED("팀이 생성되었습니다."),
    TEAM_UPDATED("팀 정보가 수정되었습니다."),
    TEAM_DELETED("팀이 삭제되었습니다."),
    TEAM_MEMBER_LIST_INQUIRE("팀 멤버 목록을 조회했습니다."),
    TEAM_MEMBER_ADDED("팀 멤버가 추가되었습니다."),
    TEAM_MEMBER_REMOVED("팀 멤버가 제거되었습니다."),
    AVAILABLE_USERS_INQUIRE("사용 가능한 사용자 목록을 조회했습니다."),

    // TASK
    TASK_CREATED("Task가 생성되었습니다."),
    TASK_LIST_INQUIRE("Task 목록을 조회했습니다."),
    TASK_OBJECT_INQUIRE("Task를 조회했습니다."),
    TASK_CONTENT_UPDATED("Task가 수정되었습니다."),
    TASK_STATUS_UPDATED("작업 상태가 업데이트되었습니다."),
    TASK_DELETED("Task가 삭제되었습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

}
