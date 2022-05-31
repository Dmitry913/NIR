package nir.backend.controllers;


import lombok.RequiredArgsConstructor;
import nir.backend.models.CallReportModel;
import nir.backend.repository.CallReportStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/callReports")
@RequiredArgsConstructor
public class CallReportController {
    private final Logger logger = LoggerFactory.getLogger(CallReportController.class);

    private final CallReportStorage callReportStorage;

    @GetMapping("/")
    public List<CallReportModel> getCallReports(@RequestParam String topic) {
        logger.info("BACK:Get callReports with topics = {}", topic);
        return callReportStorage.getCallReportByTopic(topic);
    }

    @GetMapping("/{id}")
    public CallReportModel getById(@PathVariable String id) {
        logger.info("BACK:Get callReport with id = {}", id);
        return callReportStorage.getCallReport(id);
    }

    @PostMapping("/")
    public String createCallReport(@RequestBody CallReportModel callReportModel) {
        logger.info("BACK:Create callReport");
        return callReportStorage.createCallReport(callReportModel);
    }
}