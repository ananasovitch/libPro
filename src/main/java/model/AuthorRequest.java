package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorRequest {
    private String firstName;
    private String familyName;
    private String secondName;

    public AuthorRequest(String firstName, String familyName, String secondName) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.secondName = secondName;
    }
}
















/* старый вариант import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorRequest {
    private String firstName;
    private String familyName;
    private String secondName;
}*/