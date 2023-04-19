package com.sparta.spring_lv1_assignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
    private String boardTitle;
    private String boardContents;
    private String username;
    private String password;

    @Getter
    @NoArgsConstructor
    public static class Create {
        private String boardTitle;
        private String boardContents;
        private String username;
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        private Long boardId;
        private String boardTitle;
        private String boardContents;
        private String username;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class Delete {
        private Long boardId;
        private String password;
    }
}
