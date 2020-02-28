package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values" +
            "(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    int insert(Comment comment);

    @Select("SELECT * FROM comment WHERE id = #{id}")
    Comment selectById(@Param("id") Long id);

    @Select("SELECT * FROM comment WHERE type = #{type} AND parent_id = #{parentId}  order by gmt_create desc")
    List<Comment> getTargetByid(@Param("parentId") Long parentId, @Param("type") Integer type);

    @Update("UPDATE comment SET comment_count = #{commentCount} WHERE id = #{id}")
    public void updateCommentCount(@Param("id") Long id,@Param("commentCount") Integer commentCount);

}
