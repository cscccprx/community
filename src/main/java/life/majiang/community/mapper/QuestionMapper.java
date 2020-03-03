package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    @Select("select * from question")
    List<Question> list();

    @Select("select * from question order by gmt_create desc")
    List<Question> descOrderGetList();

    @Select("select * from question where creator = #{creator} order by gmt_create desc")
    List<Question> getListById(@Param("creator")Long id);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") Long id);

    @Select("SELECT id,title,tag FROM question WHERE tag REGEXP #{tag} AND id!=#{id}")
    List<QuestionDTO> selectRelated(@Param("tag")String tag,@Param("id")Long id);

    @Select("SELECT * FROM question WHERE title REGEXP #{title}")
    List<Question> getRelatedQuestionList(@Param("title") String search);


    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    int update(Question question);

    @Update("update question set view_count = view_count+1 where id = #{id}")
    int updateView(@Param("id") Long id);

    @Update("UPDATE question SET comment_count = #{commentCount} WHERE id = #{id}")
    public void updateCommentCount(@Param("id") Long id,@Param("commentCount") Integer commentCount);
}
