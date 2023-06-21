package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ManageRestController {
    @Autowired
    private ManageService manageService;

    @PostMapping("/api/questionInsert")
    public int questionInsert(@RequestBody QuestionRecord questionRecord) {
        try {
            QuestionRecord questionId = manageService.questionInsert(questionRecord);
            return questionId.id();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/choiceInsert")
    public ResponseEntity<String> choiceInsert(@RequestBody List<ChoiceRecord> choiceRecord) {
        try {
            manageService.choiceInsert(choiceRecord);
            return new ResponseEntity<>("POST request processed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
