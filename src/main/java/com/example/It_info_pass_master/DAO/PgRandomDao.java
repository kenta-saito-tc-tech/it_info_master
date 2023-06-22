package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.SelectRandomRecord;
import com.example.It_info_pass_master.Entity.CategoryRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Entity.UserCategoryRecord;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Selection;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class PgRandomDao implements RandomDao{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private HttpSession session;

    @Override
    public List<CategoryRecord> findRandSelect(int ageId, int userId){
        System.out.println("dao ageId:"+ageId);
        System.out.println("dao userId:"+userId);
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId",ageId);
        param.addValue("userId",userId);
        String query = "SELECT * " +
                "FROM category " +
                "WHERE id IN (SELECT category_id " +
                "FROM category_select " +
                "WHERE age_id = :ageId AND " +
                "user_id = :userId AND " +
                "category_select = 1)";
        List<CategoryRecord> result = jdbcTemplate.query(query, param, new DataClassRowMapper<>(CategoryRecord.class));
        for(var a : result){
            System.out.println(a.id());
            System.out.println(a.categoryName());
        }
        return result;
    }
    @Override
    public QuestionRecord selectRandom(Integer ageId, String[] categories,Integer perfect, Integer userId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("ageId", ageId);
        param.addValue("userId", userId);
        int[] intArray = new int[categories.length];
        param.addValue("categories", intArray);
        for(var i = 0; i < categories.length; i++){
            intArray[i] = Integer.parseInt(categories[i]);
        }
        if(perfect == 1){
           String query = "select * " +
                   "from questions " +
                   "where id IN (select question_id " +
                   "from user_check " +
                   " join question_age " +
                   "on question_age_id = question_age.question_id " +
                   "where user_id = :userId " +
                   "AND age_id = :ageId) " +
                   "AND category_id = ANY(:categories) " +
                   "ORDER BY RANDOM() " +
                   "limit 1";
           var list = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
           System.out.println("randomSelect##################################################");
           System.out.println(list);
           return list.isEmpty() ? null : list.get(0);

       }else if(perfect == 0){
            String query = "select * " +
                    "from questions " +
                    "where id IN (select question_id " +
                    "from user_check " +
                    " join question_age " +
                    "on question_age_id = question_age.question_id " +
                    "where user_id = :userId " +
                    "AND perfect_check = 1 " +
                    "AND age_id = :ageId) " +
                    "AND category_id = ANY(:categories) " +
                    "ORDER BY RANDOM() " +
                    "limit 1";
            var list = jdbcTemplate.query(query, param, new DataClassRowMapper<>(QuestionRecord.class));
            System.out.println("randomSelect##################################################");
            System.out.println(list);
            return list.isEmpty() ? null : list.get(0);
       }
        return null;
    }

    @Override
    public int categorySelectUpdate(UserCategoryRecord userCategoryRecord) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        System.out.println(userCategoryRecord.ageId());
        System.out.println(userCategoryRecord.userId());
        System.out.println(userCategoryRecord.categorySelect());

        param.addValue("ageId", userCategoryRecord.ageId());
        param.addValue("userId", userCategoryRecord.userId());

//        for(var i = 0; i < userCategoryRecord.categorySelect().length; i++){
//            param.addValue("check", userCategoryRecord.categorySelect()[i]);
//            jdbcTemplate.update("UPDATE category_select " +
//                    "SET  = :check " +
//                    "WHERE question_age_id = (SELECT id " +
//                    "FROM question_age WHERE question_id = :questionId) " +
//                    "AND user_id = :userId", param);
//
//        }
        //カテゴリが変わったらiの数値も変える
        for(var i = 0; i < 6; i++){
            param.addValue("check", userCategoryRecord.categorySelect()[i]);
            param.addValue("categoryId", i+1);

            jdbcTemplate.update("UPDATE category_select " +
                    "SET category_select = :check " +
                    "WHERE user_id = :userId " +
                    "AND category_id = :categoryId " +
                    "AND age_id = :ageId", param);
        }

        return 1;
    }

    @Override
    public int sessionRandom(SelectRandomRecord selectRandomRecord){
        session.setAttribute("selectRandomRecord", selectRandomRecord);
        return 0;
    }
}
