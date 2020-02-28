package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCodeI;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public List<NotificationDTO> getNotificationByReceiverId(Long id) {
        List<Notification> notifications = notificationMapper.getNotificationByReceiverId(id);

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        return  notificationDTOS;
    }

    public Long unreadCount(Long id) {
        return notificationMapper.getUnreadCount(id);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.getNotificationById(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCodeI.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver()!=user.getId()){
            throw  new CustomizeException(CustomizeErrorCodeI.READ_NOTIFICATION_FAIL);
        }

        notificationMapper.updateStatusToRead(id, NotificationStatusEnum.READ.getStatus());
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
