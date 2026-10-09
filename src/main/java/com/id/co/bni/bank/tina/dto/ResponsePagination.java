package com.id.co.bni.bank.tina.dto;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class ResponsePagination {
    private int code;
    private String message;
    private Object data;
    private long pageNumber;
    private long totalElements;
    private long totalPages;
}
