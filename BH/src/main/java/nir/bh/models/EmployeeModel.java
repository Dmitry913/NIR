package nir.bh.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class EmployeeModel extends Person {

    public EmployeeModel(Person person) {
        super(person);
    }
}
