package pl.driver.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.driver.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
