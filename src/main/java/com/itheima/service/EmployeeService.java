package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author ASUS
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2024-06-14 10:53:26
*/
public interface EmployeeService extends IService<Employee> {

    R<Employee> login(HttpServletRequest request, Employee employee);

    R<String> saveEmployee(HttpServletRequest request, Employee employee);

    Page pageEmployee(int page, int pageSize, String name);

    R<String> updateEmployee(HttpServletRequest request, Employee employee);
}
