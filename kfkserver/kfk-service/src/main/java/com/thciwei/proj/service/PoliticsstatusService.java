package com.thciwei.proj.service;

import com.thciwei.proj.bean.Politicsstatus;
import com.thciwei.proj.mapper.PoliticsstatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticsstatusService {
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticsstaus() {
        return politicsstatusMapper.getAllPoliticsstaus();
    }
}
