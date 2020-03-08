package life.majiang.community.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.priority - ((HotTagDTO)o).getPriority();
    }
}
