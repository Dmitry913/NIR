package nir.bh.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AgreementModel {
    private String id;
    private String topic;
    private Person assigner;
}