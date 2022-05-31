package nir.client.info.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClientModel implements Serializable {

    private String clientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String workPlace;
    private Date registrationDate;

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof ClientModel) && !((ClientModel)another).clientId.equals(this.clientId)) {
            return false;
        } else {
            return true;
        }
    }
}
