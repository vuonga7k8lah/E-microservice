package com.vuongkma.media.controllers;

import com.vuongkma.media.dto.UploadDTO;
import com.vuongkma.media.helpers.APIHelper;
import com.vuongkma.media.helpers.ResponseFormat;
import com.vuongkma.media.helpers.upload.UploadStrategy;
import com.vuongkma.media.helpers.upload.UploaderFactory;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(APIHelper.restRoot)
@Slf4j
public class UploadMediaController {
    @Autowired
    private UploaderFactory uploaderFactory;

    @GetMapping
    public ResponseEntity<Object>  getAll(){
        return ResponseEntity.ok().body(ResponseFormat.build("ok"));
    }

    @PostMapping( consumes ="multipart/form-data")
    public ResponseEntity<Object>  create(@RequestParam("files") MultipartFile[] files){
        try {
            UploadStrategy uploader = uploaderFactory.getUploader("cloudinary");

            var result = uploader.setFile(files).upload();
            if(result.getStatus().equals("success")){
                //handle save database

                //handle send kafka
                return ResponseEntity.ok(ResponseFormat.build(result.getData(),"Success"));
            }
           throw new RuntimeException(result.getMessage());
        }catch (Exception exception){
            return ResponseEntity.status(400).body(ResponseFormat.build(exception.getMessage()));
        }
    }
}
