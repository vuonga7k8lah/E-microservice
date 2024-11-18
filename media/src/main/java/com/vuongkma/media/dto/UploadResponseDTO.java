package com.vuongkma.media.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UploadResponseDTO<T> {
    private String status;
    private String message;
    private Object data;

}
