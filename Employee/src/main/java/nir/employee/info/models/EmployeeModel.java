package nir.employee.info.models;

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
public class EmployeeModel implements Serializable {

    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String position;
    private Date birthday;
    private String email;

    @Override
    public boolean equals(Object another) {
        if (!(another instanceof EmployeeModel) && !((EmployeeModel)another).employeeId.equals(this.employeeId)) {
            return false;
        } else {
            return true;
        }
    }
}
