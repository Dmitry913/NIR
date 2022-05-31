package nir.backend.repository;

import nir.backend.controllers.CallReportController;
import nir.backend.error.IdNotFoundException;
import nir.backend.models.CallReportModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CallReportStorage {
    private final Logger logger = LoggerFactory.getLogger(CallReportController.class);

    private final Map<String, CallReportModel> callReports;

    public CallReportStorage() {
        this.callReports = new HashMap<>();
    }

    public List<CallReportModel> getCallReportByTopic(String topic) {
        return callReports.values().stream()
                .filter(callReportModel -> callReportModel.getTopic().contains(topic)).collect(Collectors.toList());
    }

    public CallReportModel getCallReport(String id) {
        if (callReports.containsKey(id)) {
            return callReports.get(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }


    public String createCallReport(CallReportModel callReportModel) {
        if (callReportModel.getCallReportId() == null || callReportModel.getCallReportId().isBlank()) {
            callReportModel.setCallReportId(generateUUID());
        }
        callReports.put(callReportModel.getCallReportId(), callReportModel);
        System.out.println(callReportModel.getCallReportId());
        logger.debug("id created callReport = {}", callReportModel.getCallReportId());
        return callReportModel.getCallReportId();
    }
}