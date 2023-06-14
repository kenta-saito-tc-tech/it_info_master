package com.example.It_info_pass_master.Service;

import com.example.It_info_pass_master.DAO.ManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PgManageService implements ManageService{
    @Autowired
    private ManageDao manageDao;
}
