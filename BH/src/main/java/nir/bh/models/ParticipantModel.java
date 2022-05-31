package nir.bh.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ParticipantModel {
    private String id;
    private String name;
    private TYPE_PARTICIPANT type;


    public enum TYPE_PARTICIPANT {
        EMPLOYEE, CLIENT
    }
}
