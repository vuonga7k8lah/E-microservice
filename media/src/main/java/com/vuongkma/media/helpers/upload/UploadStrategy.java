package com.vuongkma.media.helpers.upload;
import com.vuongkma.media.dto.UploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UploadStrategy {
    UploadStrategy setFile(List<MultipartFile> file);
    UploadResponseDTO upload();

}
