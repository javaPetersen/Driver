package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.Answer;
import pl.driver.driver.entity.Question;
import pl.driver.driver.repository.AnswerRepository;
import pl.driver.driver.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public void save(Question question) {
        question.setId(null);
        questionRepository.save(question);
    }

    @Override
    public ResponseEntity<Void> update(Long oldQuestionId, Question newQuestion) {
        Question oldQuestion = getIfQuestionExists(oldQuestionId);
        oldQuestion.setContent(newQuestion.getContent());
        oldQuestion.setAnswers(newQuestion.getAnswers());
        questionRepository.save(oldQuestion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> removeById(Long id) {
        Question question = getIfQuestionExists(id);
        questionRepository.delete(question);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Set<Answer>> getAllAnswersFromQuestion(Long id) {
        Question question = getIfQuestionExists(id);
        return ResponseEntity.ok(question.getAnswers());
    }

    @Override
    public ResponseEntity<Answer> getAnswerFromQuestionById(Long questionId, Long answerId) {
        Question question = getIfQuestionExists(questionId);
        Optional<Answer> optAnswer = question.getAnswers().stream()
                .filter(a -> a.getId().equals(answerId))
                .findAny();
        return ResponseEntity.of(optAnswer);
    }

    @Override
    public ResponseEntity<Void> putAnswerToQuestion(Long questionId, Long answerId) {
        Question question = getIfQuestionExists(questionId);
        Answer answer = getIfAnswerExists(answerId);
        question.getAnswers().add(answer);
        questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> removeAnswerFromQuestion(Long questionId, Long answerId) {
        Question question = getIfQuestionExists(questionId);
        Answer answer = getIfAnswerExists(answerId);
        if (!question.getAnswers().contains(answer)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        question.getAnswers().remove(answer);
        questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Question getIfQuestionExists(Long id) {
        Optional<Question> optQuestion = questionRepository.findById(id);
        if (optQuestion.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optQuestion.get();
    }

    private Answer getIfAnswerExists(Long id) {
        Optional<Answer> optAnswer = answerRepository.findById(id);
        if (optAnswer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optAnswer.get();
    }
}
