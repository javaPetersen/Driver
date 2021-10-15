package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.Question;
import pl.driver.driver.entity.Training;
import pl.driver.driver.repository.AnswerRepository;
import pl.driver.driver.repository.QuestionRepository;
import pl.driver.driver.repository.TrainingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository,
                               AnswerRepository answerRepository,
                               QuestionRepository questionRepository) {
        this.trainingRepository = trainingRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Training> findAll() {
        return trainingRepository.findAll();
    }

    @Override
    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

    @Override
    public void save(Training training) {
        training.setId(null);
        trainingRepository.save(training);
    }

    @Override
    public ResponseEntity<String> removeById(Long id) {
        if (trainingRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        trainingRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> update(Training newTraining, Long oldTrainingId) {
        Training training = checkIfTrainingExists(oldTrainingId);
        training.setAdvice(newTraining.getAdvice());
        training.setIsPassed(newTraining.getIsPassed());
        training.setPoints(newTraining.getPoints());
        training.setQuestions(newTraining.getQuestions());
        trainingRepository.save(training);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Set<Question>> getAllQuestionsFromTraining(Long id) {
        Training training = checkIfTrainingExists(id);
        return ResponseEntity.ok(training.getQuestions());
    }

    @Override
    public ResponseEntity<String> putQuestionToTraining(Long trainingId, Long questionId) {
        Training training = checkIfTrainingExists(trainingId);
        Question question = checkIfQuestionExists(questionId);
        training.getQuestions().add(question);
        trainingRepository.save(training);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> removeQuestionFromTraining(Long trainingId, Long questionId) {
        Training training = checkIfTrainingExists(trainingId);
        Question question = checkIfQuestionExists(questionId);
        if (!training.getQuestions().remove(question)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        trainingRepository.save(training);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Question> getQuestionFromTraining(Long trainingId, Long questionId) {
        Training training = checkIfTrainingExists(trainingId);
        Optional<Question> optQuestion = training.getQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findAny();
        return ResponseEntity.of(optQuestion);

    }

    private Question checkIfQuestionExists(Long questionId) {
        Optional<Question> optQuestion = questionRepository.findById(questionId);
        if (optQuestion.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optQuestion.get();
    }

    private Training checkIfTrainingExists(Long trainingId) {
        Optional<Training> optionalTraining = trainingRepository.findById(trainingId);
        if (optionalTraining.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optionalTraining.get();
    }
}
