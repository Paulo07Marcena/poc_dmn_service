package dmn_poc_service.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class DmnEvent {
    private String type;
    private Double amount;

    public DmnEvent(String type, Double amount){
        this.type = type;
        this.amount = amount;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("type", this.type);
        eventMap.put("amount", this.amount);
        return eventMap;
    }


}
