package com.vuongkma.media.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDTO {
    private MultipartFile files;
}
