package life.majiang.community.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
