package pl.driver.driver.service;

import pl.driver.driver.entity.Advice;

import java.util.List;
import java.util.Optional;

public interface AdviceService {

    void save(Advice advice);
    Optional<Advice> getAdvice(Long id);
    List<Advice> findAll();
    void remove(Long id);
    void saveAll(List<Advice> advices);
    void update(Advice editedAdvice, Long oldAdviceId);
}
