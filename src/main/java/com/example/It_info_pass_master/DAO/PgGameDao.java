package com.example.It_info_pass_master.DAO;

import com.example.It_info_pass_master.Entity.ageidRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PgGameDao implements GameDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ageidRecord userGameAdd(int userid, int ageid) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userid",userid);
        param.addValue("ageid",ageid);
        var id = jdbcTemplate.query("INSERT INTO user_game (game_date, user_id, age_id) VALUES ('2003-06-16', :userid, :ageid) RETURNING id", param,new DataClassRowMapper<>(ageidRecord.class));
        return id;
    }
}
