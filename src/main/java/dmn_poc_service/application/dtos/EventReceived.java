package dmn_poc_service.application.dtos;

import dmn_poc_service.domain.entities.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class EventReceived {
    private String accountType;
    private Double amount;

    public Event toDomain(){
        return new Event(
            this.getAccountType(),
            this.getAmount()
        );
    }
}
