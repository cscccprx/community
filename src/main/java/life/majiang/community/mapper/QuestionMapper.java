package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    @Select("select * from question ")
    List<Question> list();

    @Select("select * from question where creator = #{creator}")
    List<Question> getListById(@Param("creator")Integer id);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") Integer id);

    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    int update(Question question);
}
