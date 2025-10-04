package com.fahad.FileUpload.Service;

import com.fahad.FileUpload.Entity.FileData;
import com.fahad.FileUpload.Repository.FileRepo;
import com.fahad.FileUpload.Repository.StorageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service

public class StorageService {
    private final FileRepo fileRepo;

    public StorageService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    private final String FOLDER_PATH = "G:\\Pictures/";

    public String UploadImageToSystem(MultipartFile file) throws IOException, IllegalArgumentException{
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uniqueFileName = UUID.randomUUID().toString() + extension;
        String filePath = FOLDER_PATH + uniqueFileName;
//        String filePath = FOLDER_PATH + file.getOriginalFilename();

        FileData fileData = FileData.builder().name(uniqueFileName)
                .type(file.getContentType())
                .filePath(filePath)
                .build();

        FileData saved = fileRepo.save(fileData);

        file.transferTo(new File(filePath));

        return saved.getFilePath();
    }

    public byte[] downloadImageFromSystem(String fileName) throws IOException {
        FileData fileData = fileRepo.findAllSortedByNameUsingNative(fileName);

        if (fileData == null) {
            throw new FileNotFoundException(fileName);
        }

        return Files.readAllBytes(new File(fileData.getFilePath()).toPath());
    }
}
