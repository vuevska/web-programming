package mk.ukim.finki.wp.jan2022.g1.web;

import mk.ukim.finki.wp.jan2022.g1.model.Task;
import mk.ukim.finki.wp.jan2022.g1.model.TaskCategory;
import mk.ukim.finki.wp.jan2022.g1.model.User;
import mk.ukim.finki.wp.jan2022.g1.service.TaskService;
import mk.ukim.finki.wp.jan2022.g1.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService service;
    private final UserService userService;

    public TaskController(TaskService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    /**
     * This method should use the "list.html" template to display all entities.
     * The method should be mapped on paths '/' and '/tasks'.
     * The arguments that this method takes are optional and can be 'null'.
     *
     * @return The view "list.html".
     */
    @GetMapping(value = {"/", "/tasks"})
    public String showList(@RequestParam(required = false) Integer lessThanDayBeforeDueDate,
                           @RequestParam(required = false) Long assigneeId,
                           Model model) {
        List<Task> tasks;
        if (assigneeId == null && lessThanDayBeforeDueDate == null) {
            tasks = this.service.listAll();
        } else {
            tasks = this.service.filter(assigneeId, lessThanDayBeforeDueDate);
        }
        model.addAttribute("tasks", tasks);
        List<User> users = this.userService.listAll();
        model.addAttribute("users", users);
        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/tasks/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/tasks/add")
    public String showAdd(Model model) {
        List<Task> tasks = this.service.listAll();
        model.addAttribute("tasks", tasks);
        List<User> users = this.userService.listAll();
        model.addAttribute("users", users);
        model.addAttribute("categories", TaskCategory.values());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case all 'input' elements should be filled with the appropriate value for the entity that is updated.
     * The method should be mapped on path '/tasks/[id]/edit'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/tasks/{id}/edit")
    public String showEdit(@PathVariable Long id,Model model) {
        Task task = this.service.findById(id);
        model.addAttribute("task", task);
        List<User> users = this.userService.listAll();
        model.addAttribute("users", users);
        model.addAttribute("categories", TaskCategory.values());
        return "form";
    }

    /**
     * This method should create an entity given the arguments it takes.
     * The method should be mapped on path '/tasks'.
     * After the entity is created, the list of entities should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/tasks")
    public String create(@RequestParam String title,
                         @RequestParam String description,
                         @RequestParam TaskCategory category,
                         @RequestParam List<Long> assignees,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dueDate) {
        this.service.create(title, description, category, assignees, LocalDate.parse(dueDate));
        return "redirect:/tasks";
    }

    /**
     * This method should update an entity given the arguments it takes.
     * The method should be mapped on path '/tasks/[id]'.
     * After the entity is updated, the list of entities should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/tasks/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String description,
                         @RequestParam TaskCategory category,
                         @RequestParam List<Long> assignees) {
        this.service.update(id, title, description, category, assignees);
        return "redirect:/tasks";
    }

    /**
     * This method should delete the entities that has the appropriate identifier.
     * The method should be mapped on path '/tasks/[id]/delete'.
     * After the entity is deleted, the list of entities should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable Long id) {
        this.service.delete(id);
        return "redirect:/tasks";
    }

    /**
     * This method should mark the task that has the appropriate identifier as done.
     * The method should be mapped on path '/tasks/[id]/mark_done'.
     * After the its execution, the list of entities should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/tasks/{id}/mark_done")
    public String markDone(@PathVariable Long id) {
        this.service.markDone(id);
        return "redirect:/tasks";
    }
}
