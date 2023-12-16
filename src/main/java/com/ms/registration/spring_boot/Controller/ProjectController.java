package com.ms.registration.spring_boot.Controller;


import com.ms.registration.spring_boot.Model.Employee;
import com.ms.registration.spring_boot.Model.Project;
import com.ms.registration.spring_boot.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/newProject")
    public String newProject(Model model){
        Project project = new Project();
        model.addAttribute("create_project",project);
        return "project/create_project";
    }

    @PostMapping("/newProject")
    public String saveEmployee(@ModelAttribute Project project, Model model) {
        projectRepository.save(project);
        model.addAttribute("create_project",project);
        return "redirect:/project_list";
    }

    @GetMapping("/project_list")
    public String listProject(Model model){
        List<Project> projectList = projectRepository.findAll();
        model.addAttribute("create_project",projectList);
        return "project/project_list";
    }

    @GetMapping("/editProject/{id}")
    public String editProjectForm(@PathVariable("id") Long id, Model model){
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Illegal Argument Exception" + id));
        model.addAttribute("create_project",project);
        return "project/project_update";
    }


    @PostMapping("/editProject/{id}")
    public String saveProjectEditForm(@Valid Project project, BindingResult result, @PathVariable("id") Long id, Model model){
        if (result.hasErrors()){
            project.setId(id);
            return "project/project_update";
        }
        projectRepository.save(project);
        model.addAttribute("create_project",project);
        return "redirect:/project_list";
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        projectRepository.deleteById(id);
        return "redirect:/project_list";
    }

    @GetMapping("/viewProject/{id}")
    public String viewProjectForm(@PathVariable("id") Long id, Model model){
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Illegal Argument Exception" + id));
        model.addAttribute("create_project",project);
        return "project/project_view";
    }

    @GetMapping("/projects")
    public String getParents(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("create_project", projects);
        return "projects";
    }

}