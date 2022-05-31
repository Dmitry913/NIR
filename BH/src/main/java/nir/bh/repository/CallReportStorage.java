package nir.bh.repository;

import nir.bh.models.CallReportModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CallReportStorage {

    private final Map<String, CallReportModel> callRepots;

    public CallReportStorage() {
        this.callRepots = new HashMap<>();
    }
}
