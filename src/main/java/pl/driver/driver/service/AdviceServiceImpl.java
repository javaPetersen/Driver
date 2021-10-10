package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.repository.AdviceRepository;
import pl.driver.driver.repository.TagRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class AdviceServiceImpl implements AdviceService{

    private final AdviceRepository adviceRepository;
    private final TagRepository tagRepository;

    @Autowired
    public AdviceServiceImpl(AdviceRepository adviceRepository,
                             TagRepository tagRepository) {
        this.adviceRepository = adviceRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public void save(Advice advice) {
        tagRepository.saveAll(advice.getTags());
        adviceRepository.save(advice);
    }


    @Override
    public Optional<Advice> get(Long id) {
        return adviceRepository.findById(id);
    }

    @Override
    public List<Advice> findAll() {
        return adviceRepository.findAll();
    }

    @Override
    public Boolean remove(Long id) {
        Optional<Advice> optionalAdvice = adviceRepository.findById(id);
        if (optionalAdvice.isEmpty()) {
           return false;
        }
        adviceRepository.deleteById(id);
        return true;
    }

    @Override
    public String storeFile(MultipartFile file, Advice advice) {
        if (!Files.exists(Paths.get(uploadPath))) {
            createUploadDir();
        }
        try {
            Path newFilePath = Paths.get(uploadPath + file.getOriginalFilename());
            file.transferTo(newFilePath);
            return newFilePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Value("${upload.path}")
    private String uploadPath;

    private void createUploadDir() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }
}
