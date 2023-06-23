package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.ChoiceRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserAgeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgManageDao implements ManageDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public QuestionRecord questionInsert(QuestionRecord questionRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("questionName", questionRecord.questionName());
        param.addValue("questionText",questionRecord.questionText());
        param.addValue("answerText", questionRecord.answerText());
        param.addValue("categoryId", Integer.parseInt(questionRecord.categoryId()));
        var list = jdbcTemplate.query("insert into questions(question_name, question_text, answer_text, category_id) \n" +
                        "values (:questionName, :questionText, :answerText, :categoryId) \n" +
                        "RETURNING *"
                , param,new DataClassRowMapper<>(QuestionRecord.class));
        for (var i : list) {
            MapSqlParameterSource param2 = new MapSqlParameterSource();
            param2.addValue("questionId", i.id());
            jdbcTemplate.update("insert into question_age(age_id, question_id) values (0, :questionId)", param2);
        }
        System.out.println(list);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int choiceInsert(List<ChoiceRecord> choiceRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        var list = 0;
        for (var count : choiceRecord) {
            param.addValue("choiceText", count.choiceText());
            param.addValue("answer", count.answer());
            param.addValue("questionId",count.questionId());
             list = jdbcTemplate.update("insert into choice(choice_text, answer, question_id) \n" +
                            "values (:choiceText, :answer, :questionId)"
                    , param);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public List<UserAgeRecord> adminAllAgeSelect() {
        var ageList = jdbcTemplate.query("SELECT * FROM age", new DataClassRowMapper<>(UserAgeRecord.class));

        return ageList;
    }

}
