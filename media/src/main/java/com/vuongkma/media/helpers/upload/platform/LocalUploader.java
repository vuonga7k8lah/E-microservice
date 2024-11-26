package com.vuongkma.media.helpers.upload.platform;

import com.vuongkma.media.dto.UploadResponseDTO;
import com.vuongkma.media.helpers.upload.UploadStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocalUploader implements UploadStrategy {

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

        try {
            String uploadDir = "uploads/"; // Define the upload directory
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // Create directory if it doesn't exist

            for (MultipartFile file : files) {
                // Generate unique file name to avoid collisions
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + uniqueFileName;

                // Save file locally
                file.transferTo(new File(filePath));

                // Add file info to the response
                Map<String, String> fileInfo = new HashMap<>();
                fileInfo.put("fileName", file.getOriginalFilename());
                fileInfo.put("filePath", filePath);
                uploadedFiles.add(fileInfo);
            }

            // Set successful response
            result.setStatus("success");
            result.setMessage("Files uploaded successfully");
            result.setData(uploadedFiles);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload files locally", e);
        }
    }
}
