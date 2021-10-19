package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.File;
import pl.driver.driver.repository.FileRepository;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public ResponseEntity<String> save(MultipartFile file) {
        if (!checkContentType(file)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong file type");
        System.out.println("size" + file.getSize());
        if (file.getSize() > (1024 * 1024 * 3))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is too big");
        createUploadDirIfNotExists();
        File newFile = createFile(storeFile(file));
        fileRepository.save(newFile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileRepository.findAll());
    }

    @Override
    public ResponseEntity<File> getFileById(Long id) {
        return ResponseEntity.of(fileRepository.findById(id));
    }

    @Value("${upload.path}")
    private String uploadPath;

    private File createFile(Path path) {
        File file = new File();
        file.setFileName(path.getFileName().toString());
        file.setPath(URLEncoder.encode(path.toString(), StandardCharsets.UTF_8));
        return file;
    }

    private Path storeFile(MultipartFile file) {
        try {
            Path path = getUniquePath(file);
            byte[] bytes = file.getBytes();
            return Files.write(path, bytes);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add file");
        }
    }

    private Path getUniquePath(MultipartFile file) {
        Path path = Paths.get(uploadPath + file.getOriginalFilename());
        int version = 0;
        while (Files.exists(path)) {
            version++;
            path = Paths.get(uploadPath + version + file.getOriginalFilename());
        }
        return path;
    }

    private void createUploadDirIfNotExists() {
        if (!Files.exists(Paths.get(uploadPath))) {
            try {
                Files.createDirectory(Paths.get(uploadPath));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot create directory");
            }
        }
    }

    private Boolean checkContentType(MultipartFile file) {
        if (file.getContentType().startsWith("video") ||
                file.getContentType().startsWith("image")) {
            return true;
        }
        return false;
    }
}
