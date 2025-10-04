package com.fahad.FileUpload.Repository;

import com.fahad.FileUpload.Entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepo extends JpaRepository<ImageData, Long> {
}
