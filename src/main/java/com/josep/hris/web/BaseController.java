package com.josep.hris.web;

import com.josep.hris.entity.Employee;
import com.josep.hris.entity.Users;
import com.josep.hris.enums.RoleEnum;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseController {
    public Sort getSortForPagination(String sort, String sortType) {
        Sort sortQuery = new Sort(Sort.Direction.ASC, "id");
        if (sort != null) {
            if ("asc".equalsIgnoreCase(sortType))
                sortQuery = new Sort(Sort.Direction.ASC, sort);
            else
                sortQuery = new Sort(Sort.Direction.DESC, sort);
        }

        return sortQuery;
    }

    public boolean canDeleteThisEmployee(Employee deletedEmployee, Employee loginEmployee) {
        boolean access = false;
        List<Long> allowdRoleToDelete = new ArrayList<>();
        allowdRoleToDelete.add(RoleEnum.ADMIN.getValue());
        allowdRoleToDelete.add(RoleEnum.SUPER_ADMIN.getValue());

        Long roleIdLogin = loginEmployee.getUserId().getRoles().getId();
        if (allowdRoleToDelete.contains(roleIdLogin)) {
            if (roleIdLogin == RoleEnum.SUPER_ADMIN.getValue() || deletedEmployee.getCompanyId().getId() == loginEmployee.getCompanyId().getId()) {
                access = true;
            }
        }
        return access;
    }

    public boolean canViewThisEmployee(Employee viewEmployee, Employee loginEmployee) {
        boolean access = false;
        List<Long> allowdRoleToDelete = new ArrayList<>();
        allowdRoleToDelete.add(RoleEnum.ADMIN.getValue());
        allowdRoleToDelete.add(RoleEnum.SUPER_ADMIN.getValue());

        Long roleIdLogin = loginEmployee.getUserId().getRoles().getId();
        if (allowdRoleToDelete.contains(roleIdLogin)) {
            if (roleIdLogin == RoleEnum.SUPER_ADMIN.getValue() || viewEmployee.getCompanyId().getId() == loginEmployee.getCompanyId().getId()) {
                access = true;
            }
        }
        return access;
    }

    public Model prepareDefaultModel(Model model, Users userLogin, HashMap<String, Object> additionalData) {
        model.addAttribute("userLogin", userLogin);

        if (!additionalData.isEmpty()) {
            additionalData.forEach((key, value) -> {
                model.addAttribute(key, value);
            });
        }

        return model;
    }
}
