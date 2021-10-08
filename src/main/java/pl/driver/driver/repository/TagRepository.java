package pl.driver.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.driver.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
