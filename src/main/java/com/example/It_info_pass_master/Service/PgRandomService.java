package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.RandomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PgRandomService implements RandomService{
    @Autowired
    private RandomDao randomDao;
}
