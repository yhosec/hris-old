package com.josep.hris.web;

import com.josep.hris.entity.Company;
import com.josep.hris.entity.Users;
import com.josep.hris.helpers.Paginate;
import com.josep.hris.service.AuthService;
import com.josep.hris.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;

@Controller
public class CompanyController extends BaseController {
    @Autowired
    AuthService auth;
    @Autowired
    CompanyService companyService;

    @GetMapping("")
    public String index(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) String sortType,
            Model model,
            Principal principal
    ) {
        Users userLogin = auth.getIdentity(principal);

        Sort sortQuery = getSortForPagination(sort, sortType);

        Page<Company> companies = companyService.findAll(PageRequest.of((page-1), size, sortQuery));
        Paginate paginate = new Paginate(page, companies.getTotalPages(), companies.getTotalElements());

        HashMap<String, Object> additionalModel = new HashMap<>();
        additionalModel.put("companies", companies);
        additionalModel.put("page", page);
        additionalModel.put("sort", sort);
        additionalModel.put("sortType", sortType);
        additionalModel.put("paginate", paginate);
        model = prepareDefaultModel(model, userLogin, additionalModel);

        return "company/index";
    }
}
