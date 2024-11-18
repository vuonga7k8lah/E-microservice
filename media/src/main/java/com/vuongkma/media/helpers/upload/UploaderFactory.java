package com.vuongkma.media.helpers.upload;

import com.vuongkma.media.helpers.upload.platform.CloudinaryUpload;
import com.vuongkma.media.helpers.upload.platform.LocalUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploaderFactory {
    @Autowired
    private LocalUploader localUploader;

    @Autowired
    private CloudinaryUpload cloudinaryUploader;

    public UploadStrategy getUploader(String strategy) {
        switch (strategy.toLowerCase()) {
            case "local":
                return localUploader;
            case "cloudinary":
                return cloudinaryUploader;
            default:
                throw new IllegalArgumentException("Unknown upload strategy: " + strategy);
        }
    }
}
