package com.item.eshop.controller;
import com.item.eshop.model.Notice;
import com.item.eshop.service.NoticeService;
import com.item.eshop.util.JsonObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Api(value = "NoticeController", description = "（后台管理系统接口）营销规则增删改查等访问接口")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // ======================  admin background management system interface ( user interceptor) =============================

    @PostMapping("/add")
    public int add(@RequestParam(value = "title")String title, @RequestParam(value = "content")String content,
                      @RequestParam(value = "category")Integer category,
                      @RequestParam(value = "foreign_id")int foreign_id,
                      @RequestParam(value = "time")String time) {
        Notice notice = new Notice();
        notice.setCategory(category);
        notice.setContent(content);
        notice.setTitle(title);
        notice.setStatus(1);
        notice.setTimes(time);
        notice.setForeign_id(foreign_id);
        return noticeService.insertSelective(notice);
    }


    @PostMapping("/delete")
    public int delete(@RequestParam(value = "id")Integer id) {
        return noticeService.deleteByPrimaryKey(id);
    }

    @PostMapping("/findByCategory")
    public String findByCategory(@RequestParam(value = "category")Integer category,@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        return JsonObject.toJson(noticeService.selectMoreByCategory(category,(page-1)*num,num));
    }

    @PostMapping("/findNotice")
    public String findByUser() {
        int id = 0;
        return JsonObject.toJson(noticeService.selectByPrimaryKey(id));
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    @PostMapping("/findById")
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(noticeService.selectByPrimaryKey(id));
    }

    // add :  chan  2018/4/4
    @PostMapping("/findMore")
    public String findMore(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        System.out.println("page:"+page+",num:"+num);
        return JsonObject.toJson(noticeService.selectMore((page-1)*num,num));
    }

}
