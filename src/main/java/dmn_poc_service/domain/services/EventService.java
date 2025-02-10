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
        startDMN();
        System.out.println("DMN started");

        DmnEvent dmnEvent = new DmnEvent(event.getAccountType(), event.getAmount());

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, dmnEvent.toMap());

        Long decisionResult = result.getSingleResult().getSingleEntry();
        System.out.println("Decision Result: " + decisionResult);
    }

    public void startDMN() {
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
