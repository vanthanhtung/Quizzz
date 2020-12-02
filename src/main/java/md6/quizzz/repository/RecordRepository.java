package md6.quizzz.repository;

import md6.quizzz.model.AppUser;
import md6.quizzz.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Iterable<Record> findAllByAppUser(AppUser appUser);
}
