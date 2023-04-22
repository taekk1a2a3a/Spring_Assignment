package com.sparta.spring_lv1_assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "add")
public class StatusMessageDto<T> {

    private int statusCode; // 필드명을 statusCode로 변경
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> StatusMessageDto<T> setSuccess(int statusCode, String message, T data){
        return StatusMessageDto.add(statusCode, message, data);
    }

    public static <T> StatusMessageDto<T> setFail(int statusCode, String message){
        return StatusMessageDto.add(statusCode, message, null);
    }

}
