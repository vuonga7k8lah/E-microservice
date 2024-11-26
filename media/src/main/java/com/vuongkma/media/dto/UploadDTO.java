package com.vuongkma.media.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadDTO {
    private String id;
    private List<MultipartFile> files;
}
