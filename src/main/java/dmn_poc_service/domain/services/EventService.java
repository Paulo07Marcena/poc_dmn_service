package dmn_poc_service.domain.services;

import dmn_poc_service.domain.entities.Event;
import dmn_poc_service.domain.entities.DmnEvent;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class EventService {

    private DmnEngine dmnEngine;
    private DmnDecision decision;

    public void processEvent(Event event) {

        System.out.println("Starting to processing event");

        DmnEvent dmnEvent = new DmnEvent(event.getAccountType().toUpperCase(), event.getAmount());

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, dmnEvent.toMap());

        try {
            Object decisionResultObj = result.getSingleResult().getSingleEntry();
            Double decisionResult = null;

            if (decisionResultObj instanceof Number) {
                decisionResult = ((Number) decisionResultObj).doubleValue();
            } else {
                throw new IllegalArgumentException("Unexpected result type: " + decisionResultObj.getClass().getSimpleName());
            }

            printEventDetails(event, decisionResult);

        } catch (Exception e) {
            System.out.println("Error processing event: " + e.getMessage());
        }
    }

    private void printEventDetails(Event event, Double cashback) {
        System.out.println("+----------------+----------------------+");
        System.out.println("|     Campo      |        Valor         |");
        System.out.println("+----------------+----------------------+");
        System.out.printf("| %-14s | %-20s |\n", "Status", "Event processed");
        System.out.printf("| %-14s | %-20s |\n", "Account Type", event.getAccountType());
        System.out.printf("| %-14s | R$ %-17.2f |\n", "Amount", event.getAmount());
        System.out.printf("| %-14s | R$ %-17.2f |\n", "Cashback", cashback);
        System.out.println("+----------------+----------------------+");
    }

    public EventService() {
        this.dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        try (InputStream inputStream = getClass().getResourceAsStream("/decision.dmn")) {
            if (inputStream == null) {
                throw new RuntimeException("DMN file not found");
            }
            DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);
            this.decision = dmnEngine.parseDecision("_F3AC618E-50AC-4AAB-A82C-09E4E1054647", dmnModelInstance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DMN model", e);
        }
    }
}
