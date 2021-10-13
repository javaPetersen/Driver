package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.driver.driver.entity.Advice;
import pl.driver.driver.repository.AdviceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdviceServiceImpl implements AdviceService{

    private final AdviceRepository adviceRepository;

    @Autowired
    public AdviceServiceImpl(AdviceRepository adviceRepository) {
        this.adviceRepository = adviceRepository;
    }

    @Override
    public void save(Advice advice) {
        advice.setId(null);
        adviceRepository.save(advice);
    }

    @Override
    public Optional<Advice> getAdvice(Long id) {
        return adviceRepository.findById(id);
    }

    @Override
    public List<Advice> findAll() {
        return adviceRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        Advice advice = checkIfAdviceExists(id);
        adviceRepository.deleteById(advice.getId());
    }

    @Override
    public void saveAll(List<Advice> advices) {
        adviceRepository.saveAll(advices);
    }

    @Override
    public void update(Advice editedAdvice, Long oldAdviceId) {
        Advice oldAdvice = checkIfAdviceExists(oldAdviceId);
        oldAdvice.setTitle(editedAdvice.getTitle());
        oldAdvice.setContent(editedAdvice.getContent());
        oldAdvice.setTags(editedAdvice.getTags());
        oldAdvice.setTraining(editedAdvice.getTraining());
        adviceRepository.save(oldAdvice);
    }

    private Advice checkIfAdviceExists(Long adviceId) {
        Optional<Advice> optionalAdvice = adviceRepository.findById(adviceId);
        if (optionalAdvice.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Advice not found");
        }
        return optionalAdvice.get();
    }
}
