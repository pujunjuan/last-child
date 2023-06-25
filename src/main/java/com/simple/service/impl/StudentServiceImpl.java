package com.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.domian.Leave;
import com.simple.mapper.StudentMapper;
import com.simple.domian.Student;
import com.simple.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper =  new QueryWrapper<>();
        //无条件查询
        if(student==null){
            return this.baseMapper.selectByPage(page,queryWrapper);
        }
        //有条件查询，构建条件参数
        String name = student.getName();
        Long sno = student.getId();
        Long classId= student.getClassId();
        String sex = student.getSex();
        String card=student.getCard();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("a.name",name);
        }
        if(!"".equals(sno) && null != sno && sno !=0){
            queryWrapper.eq("a.id",sno);
        }
        if(!"".equals(classId)&&  null!=classId && classId!=0){
            queryWrapper.eq("a.class_id",classId);
        }
        if(!StringUtils.isEmpty(sex)){
            queryWrapper.eq("sex",sex);
        }
        if(!"".equals(card)&&  null!=card){
            queryWrapper.eq("card",card);
        }
        queryWrapper.eq("a.deleted",0);
        IPage<Student> StudentPage = this.baseMapper.selectByPage(page,queryWrapper);
        return  StudentPage;
    }

    @Override
    public IPage<Student> getLeaveByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        if(student==null){
            return this.baseMapper.selectLeaveOpr1(page,queryWrapper);
        }
        Long id=student.getId();
        Long classId= student.getClassId();
        Map<String, Object> time=student.getParams();
        if(time!=null){
            queryWrapper.apply(StringUtils.checkValNotNull(student.getParams().get("beginTime")),
                    "date_format (leaveList.start_time,'%Y-%m-%d') >= date_format ({0},'%Y-%m-%d')", student.getParams().get("beginTime")).
                    apply(StringUtils.checkValNotNull(student.getParams().get("endTime")),
                            "date_format (leaveList.end_time,'%Y-%m-%d') <= date_format ({0},'%Y-%m-%d')", student.getParams().get("endTime"));

        }
        if(!"".equals(classId)&&  null!=classId && classId!=0){
            queryWrapper.eq("a.class_id",classId);
        }
        if(!"".equals(id) && null!=id && id!=0){
            queryWrapper.eq("a.id",id);
        }
        queryWrapper.eq("a.deleted",0);
        return this.baseMapper.selectLeaveOpr1(page,queryWrapper);
    }

    @Override
    /**
     * 修改学生个人信息
     */
    public int updateStudent(Student student) {
        return this.baseMapper.updateStudent(student);
    }


}
