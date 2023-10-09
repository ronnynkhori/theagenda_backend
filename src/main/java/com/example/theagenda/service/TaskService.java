package com.example.theagenda.service;


import com.example.theagenda.UserRepo.TaskRepo;
import com.example.theagenda.entity.Task;
import com.example.theagenda.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    public List<Task> getAllRequestsByUserId(Integer id) {
        return taskRepo.findByUserId(id);
    }

    public Task saveRequet(Task request){
        return  taskRepo.save(request);
    }

    public Optional<Task> getAllRequestsById(Integer id) {
        return  taskRepo.findById(id);
    }

    public List<Task> getAllRequests() {
        return taskRepo.findAll();
    }

    public Task changeTaskStatus(Integer taskId, RequestStatus newStatus) {
        Optional<Task> optionalTask = taskRepo.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(newStatus);
            return taskRepo.save(task);
        } else {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        }
    }


    public void deleteTaskById(Integer taskId) {
        Optional<Task> optionalTask = taskRepo.findById(taskId);

        if (optionalTask.isPresent()) {
            taskRepo.deleteById(taskId);
        } else {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        }
    }
}
