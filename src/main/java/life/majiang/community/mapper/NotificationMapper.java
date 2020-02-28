package life.majiang.community.mapper;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("INSERT INTO notification (notifier,receiver,outerId,type,gmt_create,status,NOTIFIER_NAME,OUTER_TITLE)" +
            " VALUES (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insertReply(Notification notification);

    @Select("SELECT * FROM notification WHERE receiver = #{receiver} and status = 0 order by gmt_create desc")
    List<Notification> getNotificationByReceiverId(@Param("receiver") Long id);

    @Select("SELECT * FROM notification WHERE id = #{id}")
    Notification getNotificationById(@Param("id")Long id);

    @Select("SELECT COUNT(*) FROM notification WHERE receiver = #{receiver} and status = 0")
    Long getUnreadCount(@Param("receiver") Long id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateStatusToRead(@Param("id")Long id,@Param("status")Integer status);
}
