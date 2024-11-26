package com.vuongkma.media.helpers.upload.platform;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vuongkma.media.dto.UploadResponseDTO;
import com.vuongkma.media.helpers.upload.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
public class CloudinaryUpload implements UploadStrategy {

    @Autowired
    private Cloudinary cloudinary;

    private List<MultipartFile> files;

    @Override
    public UploadStrategy setFile(List<MultipartFile> files) {
        this.files = files;
        return this;
    }

    @Override
    public UploadResponseDTO upload() {
        var result = new UploadResponseDTO<List<Map<String, String>>>();
        List<Map<String, String>> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {

                Map<String, Object> uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto")
                );

                String url = uploadResult.get("secure_url").toString();
                Map<String, String> fileData = new HashMap<>();
                fileData.put("url", url);
                fileData.put("name", file.getOriginalFilename());
                fileData.put("type", file.getContentType());
                uploadedFiles.add(fileData);

            } catch (IOException e) {
                // Handle individual file errors
                Map<String, String> errorData = new HashMap<>();
                errorData.put("error", "Failed to upload file: " + file.getOriginalFilename());
                uploadedFiles.add(errorData);
            }
        }

        // Set final response
        result.setStatus("success");
        result.setMessage("Upload completed");
        result.setData(uploadedFiles);
        return result;
    }
}
