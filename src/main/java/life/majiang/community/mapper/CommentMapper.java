package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values" +
            "(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    int insert(Comment comment);

    @Select("SELECT * FROM comment WHERE parent_id = #{parentId}")
    Comment selectByParentId(@Param("parentId") Long parentId);


}
