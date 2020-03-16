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

    /**
     * 展示所有问题
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer page, Integer size) {
        //定义一个包含问题和导航分页栏的对象
        PaginationDTO paginationDTO = new PaginationDTO();
        //定义问题总页数
        Integer totalPage;
        //计算所有问题的个数
        Integer totalCount = questionMapper.count();
        //计算问题总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //对查询的数据库的数据进行简单的容错处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        //配置分页框样式
        paginationDTO.setPagination(totalPage, page);
        //第page页展示的第一个数据的id
        Integer offset = size * (page - 1);

        if(offset<0){
            offset=0;
        }

        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question对象所有属性拷贝到questionDTO对象
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }


    /**
     * 展示当前用户提出的问题
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //定义问题总页数
        Integer totalPage;
        //计算所有问题的个数
        Integer totalCount = questionMapper.countByUserId(userId);
        //计算问题总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //对查询的数据库的数据进行简单的容错处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        //配置分页框样式
        paginationDTO.setPagination(totalPage, page);
        //计算第page页展示的第一个数据的id
        Integer offset = size * (page - 1);
        if(offset<0){
            offset=0;
        }
        //查询数据库，返回问题集
        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question对象所有属性拷贝到questionDTO对象
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }


    /**
     * 根据id找到我们提出的某个问题
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;

    }
}
