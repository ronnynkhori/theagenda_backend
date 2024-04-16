package com.example.theagenda.service;


import com.example.theagenda.UserRepo.TaskRepo;
import com.example.theagenda.entity.Image;
import com.example.theagenda.entity.Task;
import com.example.theagenda.enums.RequestStatus;
import com.example.theagenda.model.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final FileStorageService fileStorageService;
    public List<Task> getAllRequestsByUserId(Long id) {
        return taskRepo.findByPhoneNumber(id);
    }

    public Task saveRequet(Task request){
        return  taskRepo.save(request);
    }

    public Optional<Task> getAllRequestsById(Integer id) {
        return  taskRepo.findById(id);
    }
    @Transactional
    public List<TaskDTO> getAllRequests() {
        List<Task> tasks = taskRepo.findAll();
        return tasks.stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO convertToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setFirstname(task.getFirstname());
        dto.setLastname(task.getLastname());
        dto.setPhoneNumber(task.getPhoneNumber().toString());

        List<String> base64Images = task.getImages().stream()
                .map(Image::getBase64)
                .collect(Collectors.toList());
        dto.setImageBase64s(base64Images);
        return dto;
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
    @Transactional
    public Task savedTask(String firstname, String lastname,String description,  String phoneNumber, RequestStatus status, MultipartFile[] images) {
        List<Image> storedImages = new ArrayList<>();


        for (MultipartFile file : images) {
            try {
                // Convert each file to Base64
                byte[] bytes = file.getBytes();
                String base64 = Base64.getEncoder().encodeToString(bytes);

                Image image = new Image();
                image.setBase64(base64);
                storedImages.add(image);
            } catch (IOException e) {
                throw new RuntimeException("Error processing file", e);
            }
        }

        Task task = new Task();
        task.setDescription(description);
        task.setFirstname(firstname);
        task.setLastname(lastname);
        task.setPhoneNumber(Long.valueOf(phoneNumber));
        task.setStatus(status);
        task.setImages(storedImages);

        return taskRepo.save(task);
    }



}
