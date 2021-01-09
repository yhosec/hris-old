package com.josep.hris.bean.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
