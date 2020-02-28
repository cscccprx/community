package life.majiang.community.model;

import lombok.Data;

@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private int type;
    private Long gmtCreate;
    private int status;
    private String notifierName;
    private String outerTitle;


}
