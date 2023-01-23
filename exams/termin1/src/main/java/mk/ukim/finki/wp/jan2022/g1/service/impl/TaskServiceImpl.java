package mk.ukim.finki.wp.jan2022.g1.service.impl;

import mk.ukim.finki.wp.jan2022.g1.model.Task;
import mk.ukim.finki.wp.jan2022.g1.model.TaskCategory;
import mk.ukim.finki.wp.jan2022.g1.model.User;
import mk.ukim.finki.wp.jan2022.g1.model.exceptions.InvalidTaskIdException;
import mk.ukim.finki.wp.jan2022.g1.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.jan2022.g1.repository.TaskRepository;
import mk.ukim.finki.wp.jan2022.g1.repository.UserRepository;
import mk.ukim.finki.wp.jan2022.g1.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Task> listAll() {
        return this.taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(InvalidTaskIdException::new);
    }

    @Override
    public Task create(String title, String description, TaskCategory category, List<Long> assigneesId, LocalDate dueDate) {
        List<User> assignees = this.userRepository.findAllById(assigneesId);
        Task task = new Task(title, description, category, assignees, dueDate);
        this.taskRepository.save(task);
        return task;
    }

    @Override
    public Task update(Long id, String title, String description, TaskCategory category, List<Long> assigneesId) {
        Task task = this.taskRepository.findById(id).orElseThrow(InvalidTaskIdException::new);
        task.setTitle(title);
        task.setDescription(description);
        task.setCategory(category);
        List<User> assignees = this.userRepository.findAllById(assigneesId);
        task.setAssignees(assignees);
        this.taskRepository.save(task);
        return task;
    }

    @Override
    public Task delete(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(InvalidTaskIdException::new);
        this.taskRepository.delete(task);
        return task;
    }

    @Override
    public Task markDone(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(InvalidTaskIdException::new);
        task.setDone(true);
        this.taskRepository.save(task);
        return task;
    }

    /*
    * Потребно е да овозможите пребарување на задачи според assignee и преостанати денови (dueDate < now + filtering days)*/
    @Override
    public List<Task> filter(Long assigneeId, Integer lessThanDayBeforeDueDate) {
        if (assigneeId != null && lessThanDayBeforeDueDate != null) {
            return this.taskRepository.findByAssigneesContainingAndDueDateBefore(
                    this.userRepository.findById(assigneeId).orElseThrow(InvalidUserIdException::new),
                    LocalDate.now().plusDays(lessThanDayBeforeDueDate)
            );
        } else if(assigneeId != null) {
            return this.taskRepository.findByAssigneesContaining(this.userRepository.findById(assigneeId).orElseThrow(InvalidUserIdException::new));
        } else if (lessThanDayBeforeDueDate != null) {
            return this.taskRepository.findByDueDateBefore(LocalDate.now().plusDays(lessThanDayBeforeDueDate));
        }
        return this.taskRepository.findAll();
    }
}
