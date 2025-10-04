package com.fahad.FileUpload.Controller;


import com.fahad.FileUpload.DTOs.ResponseMessage;
import com.fahad.FileUpload.Repository.StorageRepo;
import com.fahad.FileUpload.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/filedata")
public class Controller {
    private final StorageService storageService;

    public Controller(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromSystem(@PathVariable String fileName) throws IOException {
        byte [] imageData = storageService.downloadImageFromSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping(value = "/fileSystem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> UploadImageToSystem(@RequestParam("image")MultipartFile file) throws IOException {
        String message = "";

        try {
            String uploadImage = storageService.UploadImageToSystem(file);
            message = "Uploaded the Image successfully!" + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file" + file.getOriginalFilename() + " . Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
