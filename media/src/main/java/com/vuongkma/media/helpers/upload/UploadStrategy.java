package com.vuongkma.media.helpers.upload;
import com.vuongkma.media.dto.UploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;


public interface UploadStrategy {
    UploadStrategy setFile(MultipartFile[] file);
    UploadResponseDTO upload();

}
