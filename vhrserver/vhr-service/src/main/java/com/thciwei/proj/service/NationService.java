package com.thciwei.proj.service;

import com.thciwei.proj.bean.Nation;
import com.thciwei.proj.mapper.NationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
    return nationMapper.getAllNations();
    }
}
