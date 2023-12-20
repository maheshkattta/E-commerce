package mahi.labs.monkeystore.Requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {

    private String email;
    private String phone;
    private String username;
    private String password;


}
