package com.example.theagenda.controller;

import com.example.theagenda.entity.Task;
import com.example.theagenda.entity.User;
import com.example.theagenda.enums.RequestStatus;
import com.example.theagenda.model.TaskDTO;
import com.example.theagenda.service.FileStorageService;
import com.example.theagenda.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tasks/v1/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final FileStorageService fileStorageService;

    @GetMapping(path = "requests")
    public ResponseEntity<List<TaskDTO>> getAllRequests() {
        return new ResponseEntity<List<TaskDTO>>(taskService.getAllRequests(), HttpStatus.OK);
    }



    @PostMapping(path = "submit")
    public ResponseEntity<Task> submitTask(@RequestParam String firstname,
                                           @RequestParam String lastname,
                                           @RequestParam String description,
                                           @RequestParam String phoneNumber,
                                           @RequestParam  RequestStatus status,
                                           @RequestParam("images") MultipartFile[] images) {

        return new ResponseEntity<>(taskService.savedTask(firstname, lastname,description,phoneNumber,status,images), HttpStatus.CREATED);
    }


    @GetMapping(path = "requests/{id}")
    public ResponseEntity<com.example.theagenda.entity.Task> getAllRequestsById(@PathVariable Integer id) {
        Optional<Task> task = taskService.getAllRequestsById(id);

        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "user/{userId}/requests")
    public ResponseEntity<List<Task>> getAllRequestsByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getAllRequestsByUserId(userId);

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping(path = "requests/save")
    public ResponseEntity<Task> saveRequest(@RequestBody Task request) {
        return  new ResponseEntity<>(taskService.saveRequet(request), HttpStatus.CREATED);
    }


    @PutMapping(path= "{id}/change-status")
    public ResponseEntity<Task> changeTaskStatus(@PathVariable Integer id, @RequestBody RequestStatus newStatus) {
        Task updatedTask = taskService.changeTaskStatus(id, newStatus);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
