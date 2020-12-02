package md6.quizzz.service.recordService;

import md6.quizzz.model.AppUser;
import md6.quizzz.model.Record;

import java.util.Optional;

public interface RecordService {
    Iterable<Record> getAll();

    Optional<Record> getById(Long id);

    Record save(Record record);

    void deleteById(Long id);

    Iterable<Record> findAllByUser(AppUser user);
}
