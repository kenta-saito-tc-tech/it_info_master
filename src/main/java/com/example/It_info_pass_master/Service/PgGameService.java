package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.GameDao;
import org.springframework.beans.factory.annotation.Autowired;

public class PgGameService implements GameService{
    @Autowired
    private GameDao gameDao;
}
