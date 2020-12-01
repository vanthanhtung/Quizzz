package md6.quizzz.service.recordService;

import md6.quizzz.model.Record;
import md6.quizzz.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordRepository recordRepository;

    @Override
    public Iterable<Record> getAll() {
        return recordRepository.findAll();
    }

    @Override
    public Optional<Record> getById(Long id) {
        return recordRepository.findById(id);
    }

    @Override
    public Record save(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public void deleteById(Long id) {
        recordRepository.deleteById(id);
    }

}
