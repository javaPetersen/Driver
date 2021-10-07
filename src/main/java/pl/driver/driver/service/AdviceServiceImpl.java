package pl.driver.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
