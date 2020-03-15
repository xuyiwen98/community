package com.xyw.community.contorller;

import com.xyw.community.dto.PaginationDTO;
import com.xyw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class indexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        //当前页码,默认为1
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        //每页展示多少行数据,默认为5
                        @RequestParam(name = "size",defaultValue = "5")Integer size) {


        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
