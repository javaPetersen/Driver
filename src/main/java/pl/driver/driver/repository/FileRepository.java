package pl.driver.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.driver.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}
