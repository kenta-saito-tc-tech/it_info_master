package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.ageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PgQuestionDao implements QuestionDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<ageRecord> ageFindAll(){

        var ageList = jdbcTemplate.query("SELECT * FROM age",new DataClassRowMapper<>(ageRecord.class));
       System.out.print("Daoから確認");
        for(var age : ageList){
            System.out.print(age);
        }
        return ageList;
    }

}
