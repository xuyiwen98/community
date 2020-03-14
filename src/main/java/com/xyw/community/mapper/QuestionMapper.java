package com.xyw.community.mapper;


import com.xyw.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_creat,gmt_modified,creator,tag)value(#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{tag})")
    void creat(Question question);

    @Select("select * from question")
    List<Question> list();

}
