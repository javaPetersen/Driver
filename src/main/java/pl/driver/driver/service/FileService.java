package pl.driver.driver.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.driver.entity.File;

import java.util.List;

public interface FileService {
    ResponseEntity<String> save(MultipartFile file);

    ResponseEntity<List<File>> getAllFiles();

    ResponseEntity<File> getFileById(Long id);
}
