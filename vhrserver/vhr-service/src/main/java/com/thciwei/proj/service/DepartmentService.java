package com.thciwei.proj.service;

import com.thciwei.proj.bean.Department;

import com.thciwei.proj.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }


    public void deleteDepById(Department dep) {

        departmentMapper.deleteById(dep);
    }

    public List<Department> getAllDepartmentsWithOutChildren() {
    return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
