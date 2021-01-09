package com.josep.hris.bean.form;

import com.josep.hris.annotations.UniqueEmail;
import com.josep.hris.constraint.FieldMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class RegistrationForm {
    @NotEmpty
    @Size(max = 255)
    private String fullName;

    @NotEmpty
    @Size(max = 255)
    private String companyName;

    @NotEmpty
    @Size(max = 255)
    private String username;

    @NotEmpty
    @Email
    @UniqueEmail(message = "This Email Already Exsist")
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String passwordConfrim;

}
