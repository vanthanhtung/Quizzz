package md6.quizzz.controller;

import md6.quizzz.service.recordService.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RecordController {
    @Autowired
    RecordService recordService;


}
