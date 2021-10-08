package pl.driver.driver.service;

import org.springframework.web.multipart.MultipartFile;
import pl.driver.driver.entity.Advice;

import java.util.List;
import java.util.Optional;

public interface AdviceService {

    void save(Advice advice);
    Optional<Advice> get(Long id);
    List<Advice> findAll();
    Boolean remove(Long id);
    String storeFile(MultipartFile file, Advice advice);
}
