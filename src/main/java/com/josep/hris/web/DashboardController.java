package com.josep.hris.web;

import com.josep.hris.entity.Users;
import com.josep.hris.enums.RoleEnum;
import com.josep.hris.enums.CompanySetupInitialDataEnum;
import com.josep.hris.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {
    @Autowired
    private AuthService auth;

    @GetMapping("")
    public String index(Model model, Principal principal)
    {
        Users users = auth.getIdentity(principal);
        model.addAttribute("userLogin", users);
        String layout = "dashboard/index2";
//        if (users.getCompany(users).getSetupInitialData() == CompanySetupInitialDataEnum.NOT_YET.getValue()
//                && users.getRoles().getId() == RoleEnum.ADMIN.getValue()) {
//            layout = "";
//        }
        return layout;
    }
}
