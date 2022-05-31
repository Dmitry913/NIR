package nir.bh.service;

import nir.bh.models.CallReportModel;

import java.util.List;

public interface CallReportService {

    List<CallReportModel> getAllCallReport(String topic);

    CallReportModel getCallReport(String id);

    String createCallReport(CallReportModel callReportModel);
}
