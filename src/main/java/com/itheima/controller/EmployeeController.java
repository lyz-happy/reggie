package com.itheima.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.entity.Employee;
import com.itheima.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyz
 * @create 2024-06-14-11:01
 * 员工的增删改查
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 员工登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        R<Employee> reslut = employeeService.login(request,employee);
        return reslut;
    }

    // 员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    // 新增员工
    @PostMapping
    public R<String> saveEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("线程id：" + Thread.currentThread().getId());
        R<String> result = employeeService.saveEmployee(request, employee);
        return result;
    }

    // 查询员工（分页），需要配置MP的分页插件，泛型使用Page，因为前端页面需要Page
    @GetMapping("/page")
    public R<Page> pageEmployee(int page, int pageSize, String name){
        Page pageInfo = employeeService.pageEmployee(page, pageSize, name);
        return R.success(pageInfo);
    }

    // 根据id禁用或启用员工账号（页面只有admin账号展示禁用按钮，前端已实现，通过v-if等）
    @PutMapping
    public R<String> updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        R<String> r = employeeService.updateEmployee(request, employee);
        return r;
    }

    // 编辑员工信息：1.员工信息回显（根据id查询、前端VUE数据绑定）  2.修改员工信息并提交，提交到saveEmployee
    // 1.员工信息回显（根据id查询、前端VUE数据绑定）
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("未查询到相关信息");
    }

}
