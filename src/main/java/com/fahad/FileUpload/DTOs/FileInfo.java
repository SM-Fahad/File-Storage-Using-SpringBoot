package com.fahad.FileUpload.DTOs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private String name;
    private String url;
    private byte[] image;


}
