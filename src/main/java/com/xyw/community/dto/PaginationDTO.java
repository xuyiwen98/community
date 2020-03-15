package com.xyw.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    //总共的页数
    private Integer totalPage;
    //当前在第几页
    private Integer page;
    //底部页码导航栏
    private List<Integer> pages = new ArrayList<>();


    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;

        //计算pages中展示的内容
        //展示逻辑:当前页面向前展示3个,向后展示3个;如果不足3个就有多少显示多少
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                //头部插入
                pages.add(0,page - i);
            }
            if (page + i <= totalPage) {
                //尾部插入
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否展示下一页
        if (page.equals(totalPage)) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
