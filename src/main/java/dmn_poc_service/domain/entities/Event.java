package dmn_poc_service.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Event {
    private String accountType;
    private Double amount;

    public Event(String accountType, Double amount){
        this.accountType = accountType;
        this.amount = amount;
    }
}
