package com.simple.domian;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.service.ParentService;
import com.simple.service.StudentService;
import com.simple.service.UserService;
import com.simple.util.PinYinUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@SpringBootTest
public class TeacherMapperTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    ParentService parentService;

    @Test
    public void testSelect1() {
        LambdaUpdateWrapper<Student> queryWrapper=new LambdaUpdateWrapper<Student>();
        Student student=new Student();
        student.setId(1);
        student.setName("蒲新");
        studentService.updateStudent(student);
        Student student1=studentService.getById(1);
        System.out.println(student1);
        Long sId=student1.getUsId();
        String name= PinYinUtil.getPinyin(student1.getName());
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",sId).set("name",name);
        userService.update(updateWrapper);
        System.out.println(userService.getById(student1.getUsId()));
    }
    @Test
    public void testSelect() {
        Collection<Long> arrayList=new ArrayList<>();
        Collection<Long> arrayList1=new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            arrayList.add((long) i);
        }
        List<Student> list = studentService.listByIds(arrayList);
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            arrayList1.add((list.get(i).getUsId()));
        }
        System.out.println("arrayList1"+arrayList1);
    }

    @Test
    public void test03(){
        Parent parent=new Parent();
        parent.setName("王海");
        parent.setCard("432058199610025689");
        parent.setSex("男");
        System.out.println("11111");
        boolean flag=parentService.save(parent);
        System.out.println("11111");
        System.out.println("flag"+flag);
    }

    @Test
    public void test04(){
        Page<Student> page = new Page<>(1,5);
        Student student=new Student();
        student.setName("心");
        IPage<Student> us=studentService.getStudentByOpr(page,student);
        System.out.println(us);
        List<Student> list = us.getRecords();
        for(Student user : list){
            System.out.println(user);
        }
    }

}
