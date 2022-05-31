package nir.bh.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nir.bh.error.MappingException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Person {
    protected String id;
    protected String firstName;
    protected String middleName;
    protected String lastName;

    public Person(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.middleName = person.getMiddleName();
        this.lastName = person.getLastName();
    }

    public void setFromFullName(String fullName) {
        if (fullName == null) {
            throw new MappingException();
        }
        String[] partName = fullName.split(" ");
        firstName = partName[0];
        middleName = partName[1];
        lastName = partName[2];
    }
}
