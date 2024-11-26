package com.vuongkma.media.controllers;

import com.vuongkma.media.dto.ProductMediaProducerDTO;
import com.vuongkma.media.dto.UploadDTO;
import com.vuongkma.media.entities.MediaEntity;
import com.vuongkma.media.helpers.APIHelper;
import com.vuongkma.media.helpers.ResponseFormat;
import com.vuongkma.media.helpers.upload.UploadStrategy;
import com.vuongkma.media.helpers.upload.UploaderFactory;
import com.vuongkma.media.services.MediaKafkaProducerService;
import com.vuongkma.media.services.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(APIHelper.restRoot)
@Slf4j
public class UploadMediaController {
    @Autowired
    private UploaderFactory uploaderFactory;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MediaKafkaProducerService mediaKafkaProducerService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(ResponseFormat.build("ok"));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Object> create(@ModelAttribute UploadDTO uploadDTO) {
        try {
            UploadStrategy uploader = uploaderFactory.getUploader("cloudinary");

            // Upload file
            var result = uploader.setFile(uploadDTO.getFiles()).upload();

            if (!"success".equals(result.getStatus())) {
                throw new RuntimeException(result.getMessage());
            }

            // Kiểm tra dữ liệu trả về
            if (!(result.getData() instanceof List<?>)) {
                throw new IllegalArgumentException("Data is not a List");
            }

            // Sử dụng stream song song để xử lý nhanh hơn
            List<?> dataResult = (List<?>) result.getData();
            List<ProductMediaProducerDTO> kafkaMessages = dataResult.parallelStream()
                    .map(resultFile -> processUploadedFileParallel(resultFile, uploadDTO.getId()))
                    .toList();


            return ResponseEntity.ok(ResponseFormat.build(result.getData(), "Success"));

        } catch (Exception exception) {
            return ResponseEntity.status(400).body(ResponseFormat.build(exception.getMessage()));
        }
    }

    /**
     * Xử lý file đơn lẻ (song song)
     */
    private ProductMediaProducerDTO processUploadedFileParallel(Object resultFile, String uploadId) {
        // Map resultFile thành MediaEntity
        MediaEntity mediaEntity = modelMapper.map(resultFile, MediaEntity.class);

        // Lưu vào database
        MediaEntity savedMedia = mediaService.insert(mediaEntity);

        // Tạo DTO cho Kafka
        ProductMediaProducerDTO productMediaProducerDTO = new ProductMediaProducerDTO();
        productMediaProducerDTO.setMediaId(savedMedia.getId().toString());
        productMediaProducerDTO.setUrl(savedMedia.getUrl());
        productMediaProducerDTO.setId(uploadId);

        // Gửi batch Kafka message (nếu Kafka hỗ trợ batch processing)
        mediaKafkaProducerService.sendMessage(productMediaProducerDTO);

        return productMediaProducerDTO;
    }

}
