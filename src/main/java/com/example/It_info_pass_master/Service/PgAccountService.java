package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PgAccountService implements AccountService{
    @Autowired
    private AccountDao accountDao;
}
