package com.xyw.community.service;

import com.xyw.community.dto.PaginationDTO;
import com.xyw.community.dto.QuestionDTO;
import com.xyw.community.mapper.QuestionMapper;
import com.xyw.community.mapper.UserMapper;
import com.xyw.community.model.Question;
import com.xyw.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        //对查询的数据库的数据进行简单的容错处理
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalePage()){
            page=paginationDTO.getTotalePage();
        }


        //第page页展示的第一个数据的id
        Integer offset=size*(page-1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question对象所有属性拷贝到questionDTO对象
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);




        return paginationDTO;
    }
}
