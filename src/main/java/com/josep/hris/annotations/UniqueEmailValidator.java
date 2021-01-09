package com.josep.hris.annotations;

import com.josep.hris.entity.Users;
import com.josep.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        boolean isUniqueEmail = false;
        Users user = userService.findByEmail(email);
        System.out.println(user.getId());
        System.out.println(email);
        if (user == null) {
            isUniqueEmail = true;
        }
        System.out.println(isUniqueEmail);
        return isUniqueEmail;
    }
}
