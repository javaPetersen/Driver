package pl.driver.driver.service;

import org.springframework.http.ResponseEntity;
import pl.driver.driver.entity.Question;
import pl.driver.driver.entity.Training;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrainingService {

    List<Training> findAll();
    Optional<Training> findById(Long id);
    void save(Training training);
    ResponseEntity<String> removeById(Long id);
    ResponseEntity<String> update(Training newTraining, Long oldTrainingId);
    ResponseEntity<Set<Question>> getAllQuestionsFromTraining(Long id);
    ResponseEntity<String> putQuestionToTraining(Long trainingId, Long questionId);
    ResponseEntity<Void> removeQuestionFromTraining(Long trainingId, Long questionId);
    ResponseEntity<Question> getQuestionFromTraining(Long trainingId, Long questionId);
}
