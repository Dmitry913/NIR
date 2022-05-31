package nir.bh.controllers;


import lombok.RequiredArgsConstructor;
import nir.bh.BhApplication;
import nir.bh.models.CallReportModel;
import nir.bh.service.CallReportService;
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
@RequestMapping(value = "/callReport")
@RequiredArgsConstructor
public class CallReportController {
    private final Logger logger = LoggerFactory.getLogger(BhApplication.class);

    private final CallReportService callReportService;

    @GetMapping("/")
    public List<CallReportModel> getCallReports(@RequestParam String topic) {
        logger.info("Get callReports with topics = {}", topic);
        return callReportService.getAllCallReport(topic);
    }

    @GetMapping("/{id}")
    public CallReportModel getById(@PathVariable String id) {
        System.out.println(id);
        logger.info("Get callReport with id = {}", id);
        return callReportService.getCallReport(id);
    }

    @PostMapping("/")
    public String createCallReport(@RequestBody CallReportModel callReportModel) {
        logger.info("Create callReport");
        return callReportService.createCallReport(callReportModel);
    }
}