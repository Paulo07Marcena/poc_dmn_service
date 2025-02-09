package dmn_poc_service.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Event {
    private String id;
    private String type;
    private String purchaseId;
    private Double amount;

    public Event(String id, String type, String purchaseId, Double amount){
        this.id = id;
        this.type = type;
        this.purchaseId = purchaseId;
        this.amount = amount;
    }
}
