package com.simple.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.result.CommonPage;
import com.simple.common.result.Result;
import com.simple.domian.CookBook;
import com.simple.service.CookBookService;
import com.simple.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cook")
public class CookBookController {

    @Autowired
    private CookBookService cookBookService;
    @Autowired
    private OssService fileUploadService;
    /**
     * 分页条件查询食谱信息
     * @param pageNo
     * @param pageSize
     * @param cookBook
     * @return
     */
    @GetMapping("/getCookBookOpr")
    public Result getCookBookOpr(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                CookBook cookBook){
        System.out.println(cookBook);
        Page<CookBook> page = new Page<>(pageNo,pageSize);
        IPage<CookBook> courseIPage=cookBookService.getCookBookOpr(page,cookBook);
        CommonPage commonPage=new CommonPage();
        commonPage.setPageNum(pageNo);
        commonPage.setPageSize(pageSize);
        commonPage.setTotal(courseIPage.getTotal());
        commonPage.setList(courseIPage.getRecords());
        //封装Result返回
        return Result.success(commonPage);
    }

    /**
     * 返回今日食谱
     * @param
     * @return
     */
    @GetMapping("/getCookBookDay")
    public Result getCookBookDay(){
        QueryWrapper<CookBook> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply(true, "TO_DAYS(NOW())-TO_DAYS(create_time) = 0");
        return Result.success(cookBookService.list(queryWrapper));
    }

    /**
     * 根据id获取食谱信息
     * @param id
     * @return
     */
    @GetMapping("/getCookBookById")
    public Result getCookBookById(@RequestParam Long id){
        CookBook cookBook=cookBookService.getById(id);
        if (cookBook!=null){
            return Result.success(cookBook);
        }
        return Result.error();
    }

    /**
     * 添加每日食谱
     * @param
     * @return
     */
    @PostMapping("/addCookBook")
    public Result addCookBook(@RequestParam String title,
                              @RequestParam String type,
                              @RequestParam String list,
                              @RequestParam String canalyze,
                              @RequestParam("file") MultipartFile file){
        String photo=fileUploadService.upload(file).getName();
        System.out.println(photo);
        CookBook cookBook=new CookBook();
        cookBook.setCanalyze(canalyze);
        cookBook.setList(list);
        cookBook.setTitle(title);
        cookBook.setType(type);
        cookBook.setImage(photo);
        cookBook.setCreateTime(new Date());
        boolean flag=cookBookService.save(cookBook);
        if (flag){
            return Result.success();
        }
        return Result.error();

    }
    /**
     * 修改食谱内容
     * @param
     * @return
     */
    @PostMapping("/updateCookBook")
    public Result updateCookBook(@RequestParam Long id,
                                 @RequestParam String title,
                                 @RequestParam String type,
                                 @RequestParam String list,
                                 @RequestParam String canalyze,
                                 @RequestParam("file") MultipartFile file){
        String photo=fileUploadService.upload(file).getName();
        CookBook cookBook=new CookBook();
        cookBook.setId(id);
        cookBook.setCanalyze(canalyze);
        cookBook.setList(list);
        cookBook.setTitle(title);
        cookBook.setType(type);
        cookBook.setImage(photo);
        boolean flag=cookBookService.updateCookBook(cookBook);
        if (flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 修改食谱内容
     * @param
     * @return
     */
    @PutMapping("/updateCookBook1")
    public Result updateCookBook1(@RequestBody CookBook cookBook){
        boolean flag=cookBookService.updateCookBook(cookBook);
        if (flag){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 修改食谱图片
     * @param id
     * @param file
     * @return
     */
    @PutMapping("/updateCookBookImg")
    public Result updateCookBookImg(@RequestParam Long id,@RequestParam("file") MultipartFile file){
        String photo=fileUploadService.upload(file).getName();
        CookBook cookBook=new CookBook();
        cookBook.setId(id);
        cookBook.setImage(photo);
        boolean flag=cookBookService.updateCookBook(cookBook);
        if (flag){
            return Result.success(cookBookService.getById(id));
        }
          return Result.error();
    }

    /**
     * 根据食谱id删除信息
     * @param id
     * @return
     */
    @DeleteMapping("/delCookBookById")
    public Result delCookBookById(@RequestParam Long id){
        boolean flag=cookBookService.removeById(id);
        if (flag){
            return Result.success();
        }
        return Result.error();

    }


    /**
     * 根据食谱id批量删除信息
     * @param ids
     * @return
     */
    @DeleteMapping("/delCookBookByIds")
    public Result delCookBookByIds(@RequestParam List<Long> ids){
        boolean flag=cookBookService.removeByIds(ids);
        if (flag){
            return Result.success();
        }
        return Result.error();
    }
}
