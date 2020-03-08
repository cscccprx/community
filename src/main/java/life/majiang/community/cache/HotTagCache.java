package life.majiang.community.cache;

import life.majiang.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private Map<String,Integer> tags = new HashMap<>();
    private List<String> tagDTOS;

    public void update(Map<String,Integer> tags){
        int max =10;
        PriorityQueue<HotTagDTO> queue = new PriorityQueue<>(max);

        tags.forEach((name,priority)->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (queue.size()<max){
                queue.add(hotTagDTO);
            }else {
                HotTagDTO minHot = queue.peek();
                if (hotTagDTO.compareTo(minHot)>0){
                    queue.poll();
                    queue.add(hotTagDTO);
                }
            }
        });

        ArrayList<String> objects = new ArrayList<>();
        HotTagDTO poll = queue.poll();
        while (poll!=null){
            objects.add(0,poll.getName());
            poll = queue.poll();
        }
        tagDTOS = objects;
        System.out.println(tagDTOS);


    }
}
