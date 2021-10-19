package pl.driver.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.driver.entity.File;
import pl.driver.driver.service.FileService;

import java.util.List;

@RestController
@RequestMapping("api/upload")
public class FileRestController {

    private final FileService fileService;

    @Autowired
    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<File>> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable Long id) {
        return fileService.getFileById(id);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        if (file.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return fileService.save(file);
    }


}
