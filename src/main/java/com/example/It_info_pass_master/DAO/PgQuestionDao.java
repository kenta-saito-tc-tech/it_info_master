package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.AgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PgQuestionDao implements QuestionDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<AgeRecord> ageFindAll(){

        var ageList = jdbcTemplate.query("SELECT * FROM age",new DataClassRowMapper<>(AgeRecord.class));

        for(var age : ageList){
            System.out.print(age);
        }
        return ageList;
    }

    @Override
    public List<QuestionRecord> selectQuestion(int ageId, int categoryId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        param.addValue("categoryId", categoryId);

        String query = "select * " +
                "from questions " +
                "where id IN (select question_id " +
                "from question_age " +
                "where age_id = :ageId) " +
                "AND category_id = :categoryId";
        List<QuestionRecord> result = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
        return result;
    }

}
