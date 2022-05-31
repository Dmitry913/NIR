package nir.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CallReportModel implements Serializable {

    private String callReportId;
    private String topic;
    private List<String> clients;
    private List<String> employees;
    private List<AgreementModel> agreements;
    private String description;

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof CallReportModel) && !((CallReportModel)another).callReportId.equals(this.callReportId)) {
            return false;
        } else {
            return true;
        }
    }
}
