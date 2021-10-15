package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.Answer;
import pl.driver.driver.repository.AnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void save(Answer answer) {
        answer.setId(null);
        answerRepository.save(answer);
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public ResponseEntity<Void> update(Long oldAnswerId, Answer newAnswer) {
        Answer oldAnswer = checkIfAnswerExists(oldAnswerId);
        oldAnswer.setContent(newAnswer.getContent());
        oldAnswer.setIsCorrect(newAnswer.getIsCorrect());
        answerRepository.save(oldAnswer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Answer checkIfAnswerExists(Long id) {
        Optional<Answer> optAnswer = answerRepository.findById(id);
        if (optAnswer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optAnswer.get();
    }

    @Override
    public ResponseEntity<Void> removeById(Long id) {
        if (answerRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        answerRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
