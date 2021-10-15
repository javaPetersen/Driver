package pl.driver.driver.service;

import org.springframework.http.ResponseEntity;
import pl.driver.driver.entity.Answer;
import pl.driver.driver.entity.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionService {
    List<Question> findAll();
    Optional<Question> findById(Long id);
    void save(Question question);
    ResponseEntity<Void> update(Long oldQuestionId, Question newQuestion);
    ResponseEntity<Void> removeById(Long id);
    ResponseEntity<Set<Answer>> getAllAnswersFromQuestion(Long id);
    ResponseEntity<Answer> getAnswerFromQuestionById(Long questionId, Long answerId);
    ResponseEntity<Void> putAnswerToQuestion(Long questionId, Long answerId);
    ResponseEntity<Void> removeAnswerFromQuestion(Long questionId, Long answerId);
}
