package com.id.co.bni.bank.tina.dto;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class Response {
    private int code;
    private Object data;
    private String message;
}
