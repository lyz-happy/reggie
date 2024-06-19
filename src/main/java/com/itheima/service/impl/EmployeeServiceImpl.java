package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.R;
import com.itheima.entity.Employee;
import com.itheima.service.EmployeeService;
import com.itheima.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

/**
* @author ASUS
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2024-06-14 10:53:26
*/
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    // 登录
    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if (null == emp) {
            return R.error("登录失败");
        }
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    // 新增员工
    @Override
    public R<String> saveEmployee(HttpServletRequest request ,Employee employee) {
        log.info("线程id：" + Thread.currentThread().getId());
        // 初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 字段补充
        //employee.setCreateTime(new Date());
        //employee.setUpdateTime(new Date());
        //employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        //employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        //
        employeeMapper.insert(employee);
        return R.success("新增成功");
    }

    // 查询员工（分页）
    @Override
    public Page pageEmployee(int page, int pageSize, String name) {
        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeMapper.selectPage(pageInfo,queryWrapper);
        // 返回分页查询结果
        return pageInfo;    // pageInfo无需我们自己封装
    }

    // 修改员工信息
    @Override
    public R<String> updateEmployee(HttpServletRequest request, Employee employee) {
        // 补充字段
        employee.setUpdateTime(new Date());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        employeeMapper.updateById(employee);
        return R.success("员工信息修改成功");
    }
}




