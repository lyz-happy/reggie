package com.itheima.mapper;

import com.itheima.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2024-06-14 10:53:26
* @Entity com.itheima.entity.Employee
 * MyBatisX逆向工程不会自动在EmployeeMapper extends BaseMapper<Employee>加上@Mapper注解，可能报错，可以手动加上
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




