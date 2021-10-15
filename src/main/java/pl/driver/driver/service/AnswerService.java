package pl.driver.driver.service;

import org.springframework.http.ResponseEntity;
import pl.driver.driver.entity.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    void save(Answer answer);
    Optional<Answer> findById(Long id);
    List<Answer> findAll();
    ResponseEntity<Void> update(Long oldAnswerId, Answer newAnswer);
    ResponseEntity<Void> removeById(Long id);
}
