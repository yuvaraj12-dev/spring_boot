package com.ms.registration.spring_boot.Controller;

import com.ms.registration.spring_boot.Model.Employee;
import com.ms.registration.spring_boot.Model.Project;
import com.ms.registration.spring_boot.Repository.EmployeeRepository;
import com.ms.registration.spring_boot.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class EmpController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/new")
    public String newEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee_create",employee);
        List<Project> project =  projectRepository.findAll();
        model.addAttribute("employee_project",project);
        return "employee/employee_create";
    }

    @PostMapping("/new")
    public String saveEmployee(@ModelAttribute Employee employee, Model model) {
        employeeRepository.save(employee);
        model.addAttribute("employee_create",employee);
        return "redirect:/employee_list";
    }

    @GetMapping("/employee_list")
    public String listEmployee(Model model){
        List<Employee> employeeList = employeeRepository.findAll();
        model.addAttribute("employee_create",employeeList);
        return "employee/employee_list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Illegal Argument Exception" + id));
        model.addAttribute("employee_create",employee);
        List<Project> project =  projectRepository.findAll();
        model.addAttribute("employee_project",project);
        return "employee/employee_update";
    }

    @PostMapping("/edit/{id}")
    public String saveEditForm(@Valid Employee employee, BindingResult result, @PathVariable("id") Long id, Model model){
        if (result.hasErrors()){
            employee.setId(id);
            return "employee/employee_update";
        }
        employeeRepository.save(employee);
        model.addAttribute("employee_create",employee);
        return "redirect:/employee_list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        employeeRepository.deleteById(id);
        return "redirect:/employee_list";
    }

    @GetMapping("/view/{id}")
    public String viewForm(@PathVariable("id") Long id,Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Illegal Argument Exception" + id));
        model.addAttribute("employee_create",employee);
        return "employee/employee_view";
    }
}