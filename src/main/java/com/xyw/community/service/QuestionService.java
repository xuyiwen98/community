package com.xyw.community.service;

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

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question对象所有属性拷贝到questionDTO对象
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
