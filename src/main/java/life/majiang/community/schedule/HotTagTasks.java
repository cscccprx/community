package life.majiang.community.schedule;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.cache.HotTagCache;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;


    @Scheduled(fixedRate =10000)
    public void hotTagSchedule() {
        int offset = 1 ;
        int limit = 5;
        log.info("hotTagSchedule start {}",new Date());
        List<Question> list = new ArrayList<>();
        Map<String,Integer> priorities = new HashMap<>();
        while (offset == 1 || list.size()==limit){
            Page page = PageHelper.startPage(offset,limit);
            list = questionMapper.list();
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority!=null){
                        priorities.put(tag,priority+5+question.getCommentCount());
                    }else {
                        priorities.put(tag,5+question.getCommentCount());
                    }
                }
            }

            offset++;

        }
        priorities.forEach((k,v)-> {
            System.out.println(k + ":" + v);
        });
        hotTagCache.update(priorities);
        log.info("The time is now {}", new Date());
    }
}
