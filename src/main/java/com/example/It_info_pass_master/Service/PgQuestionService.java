package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PgQuestionService implements QuestionService{
    @Autowired
    private QuestionDao questionDao;
}
