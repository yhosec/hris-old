package com.josep.hris.web;

import com.josep.hris.bean.form.EmployeeForm;
import com.josep.hris.entity.Company;
import com.josep.hris.entity.Employee;
import com.josep.hris.entity.Users;
import com.josep.hris.enums.RoleEnum;
import com.josep.hris.helpers.Paginate;
import com.josep.hris.repository.EmployeePagingRepository;
import com.josep.hris.repository.EmployeeRepository;
import com.josep.hris.service.AuthService;
import com.josep.hris.service.EmployeeService;
import com.josep.hris.service.RoleService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionAttributes("message")
@Controller
@RequestMapping("employee")
public class EmployeeController extends BaseController {
    @Autowired
    private Validator validator;

    @Autowired
    private AuthService auth;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String index(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) String sortType,
            Model model,
            Principal principal) {

        Users userLogin = auth.getIdentity(principal);

        Sort sortQuery = getSortForPagination(sort, sortType);
        Page<Employee> employees = employeeService.findAll(userLogin.getCompany(userLogin), PageRequest.of((page-1), size, sortQuery));
        Paginate paginate = new Paginate(page, employees.getTotalPages(), employees.getTotalElements());

        HashMap<String, Object> additionalModel = new HashMap<>();
        additionalModel.put("employees", employees);
        additionalModel.put("page", page);
        additionalModel.put("sort", sort);
        additionalModel.put("sortType", sortType);
        additionalModel.put("paginate", paginate);

        model = prepareDefaultModel(model, userLogin, additionalModel);
        return "employee/index";
    }

    @GetMapping("create")
    public String create(Model model, Principal principal) {
        Users userLogin = auth.getIdentity(principal);
        EmployeeForm employee = new EmployeeForm();
        HashMap<String, Object> additionalModel = new HashMap<>();
        additionalModel.put("employee", employee);
        additionalModel.put("roles", roleService.findActiveRole());
        model = prepareDefaultModel(model, userLogin, additionalModel);
        return "employee/create";
    }

    @GetMapping("edit/{employeeId}")
    public String edit(@PathVariable Long employeeId, Model model, Principal principal, final RedirectAttributes redirectAttributes) {
        String returnView = "employee/edit";
        Users userLogin = auth.getIdentity(principal);
        EmployeeForm employeeForm = new EmployeeForm();
        Employee dataEmployee = employeeService.findById(employeeId);
        if (dataEmployee != null) {
            if (dataEmployee.getUserId() != null) {
                employeeForm.setEmail(dataEmployee.getUserId().getEmail());
                employeeForm.setUsername(dataEmployee.getUserId().getUsername());
                employeeForm.setRole(dataEmployee.getUserId().getRoles());
            }
            employeeForm.setFullName(dataEmployee.getFullName());
            employeeForm.setAddress(dataEmployee.getAddress());
            employeeForm.setPhone(dataEmployee.getPhone());
            employeeForm.setBarcode(dataEmployee.getBarcode());
            employeeForm.setNik(dataEmployee.getNik());
            HashMap<String, Object> additionalModel = new HashMap<>();
            additionalModel.put("employee", employeeForm);
            additionalModel.put("employeeId", employeeId);
//        additionalModel.put("roles", roleService.findActiveRole());
            model = prepareDefaultModel(model, userLogin, additionalModel);
        } else {
            redirectAttributes.addFlashAttribute("message", "Employee Not Found");
            returnView = "redirect:/employee";
        }

        return returnView;
    }

    @PostMapping("edit/{employeeId}")
    public String doEdit(@PathVariable Long employeeId,
                           @ModelAttribute("employee") EmployeeForm employeeForm,
                           final RedirectAttributes redirectAttributes,
                           Model model,
                           BindingResult result,
                           Principal principal) {
        Users userLogin = auth.getIdentity(principal);
        Company company = userLogin.getCompany(userLogin);
        employeeForm.setCompany(company);
        employeeForm.setCreateBy(userLogin);

        validator.validate(employeeForm, result);
        Employee saveEmployee = employeeService.updateEmployee(employeeId, employeeForm);
        if (saveEmployee != null) {
            redirectAttributes.addFlashAttribute("message", "Update Employee Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Update Employee Failed");
        }
        return "redirect:/employee";
    }

    @PostMapping("create")
    public String doCreate(@ModelAttribute("employee") EmployeeForm employeeForm, final RedirectAttributes redirectAttributes, Model model, BindingResult result,
                           Principal principal) {
        Users userLogin = auth.getIdentity(principal);
        Company company = userLogin.getCompany(userLogin);
        employeeForm.setCompany(company);
        employeeForm.setCreateBy(userLogin);

        validator.validate(employeeForm, result);
        Employee saveEmployee = employeeService.addEmployee(employeeForm);
        if (saveEmployee != null) {
            redirectAttributes.addFlashAttribute("message", "Create Employee Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Create Employee Failed");
        }
        return "redirect:/employee";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {
        Users userLogin = auth.getIdentity(principal);
        Company companyLogin = userLogin.getCompany(userLogin);

        List<Long> allowdRoleToDelete = new ArrayList<>();
        allowdRoleToDelete.add(RoleEnum.ADMIN.getValue());

        if (allowdRoleToDelete.contains(userLogin.getRoles().getId())) {
            Employee deletedEmployee = employeeService.findById(id);

            if (canDeleteThisEmployee(deletedEmployee, userLogin.getEmployee())) {
                Employee employee = employeeService.delete(id);
                if (employee != null) {
                    System.out.println("Delete Employee Success");
                    redirectAttributes.addFlashAttribute("message", "Delete Employee Success");
                } else {
                    System.out.println("Delete Employee Failed");
                    redirectAttributes.addFlashAttribute("message", "Delete Employee Failed");
                }
            } else {
                System.out.println("You canot delete employee from other company");
                redirectAttributes.addFlashAttribute("message", "You canot delete employee from other company");
            }

        } else {
            System.out.println("You have no permission for delete this employee");
            redirectAttributes.addFlashAttribute("message", "You have no permission for delete this employee");
        }

        return "redirect:/employee";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        String view = "employee/detail";
        Users userLogin = auth.getIdentity(principal);
        Employee viewEmployee = employeeService.findById(id);
        if (viewEmployee != null) {
            if (canViewThisEmployee(viewEmployee, userLogin.getEmployee())) {
                model.addAttribute("employee", viewEmployee);
            } else {
                redirectAttributes.addFlashAttribute("message", "You canot access this data");
                view = "redirect:/employee";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Employee not found.");
            view = "redirect:/employee";
        }

        HashMap<String, Object> additionalModel = new HashMap<>();
        additionalModel.put("employee", viewEmployee);
        model = prepareDefaultModel(model, userLogin, additionalModel);
        return view;
    }

    @GetMapping("pdf")
    @ResponseBody
    public void pdf(HttpServletResponse response) throws JRException, IOException {
        Map<String,Object> params = new HashMap<>();
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/report_employee_list_pdf.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((List<Employee>) employeeService.findAll());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=employee-list.pdf");
        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping("xls")
    @ResponseBody
    public void xls(HttpServletResponse response) throws JRException, IOException {
        Map<String,Object> params = new HashMap<>();
        InputStream employeeReportStream = getClass().getResourceAsStream("/report/report_employee_list_xlsx.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((List<Employee>) employeeService.findAll());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
        response.setHeader("Content-Disposition", "attachment; filename=employee-list.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        JRXlsxExporter xlsExporter = new JRXlsxExporter(DefaultJasperReportsContext.getInstance());
        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        xlsExporter.exportReport();

    }
}
