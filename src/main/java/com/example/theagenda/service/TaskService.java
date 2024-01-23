package com.example.theagenda.service;


import com.example.theagenda.UserRepo.TaskRepo;
import com.example.theagenda.entity.Image;
import com.example.theagenda.entity.Task;
import com.example.theagenda.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Task savedTask(String description, String firstname, String lastname, String phoneNumber, RequestStatus status, MultipartFile[] images) {
        List<Image> storedImages = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // Define the date format

        for (MultipartFile file : images) {
            try {
                String originalFilename = file.getOriginalFilename();


                String timestamp = dateFormat.format(new Date()); // Get current timestamp
                String newFilename = timestamp + "_" + originalFilename; // Construct new filename with timestamp

                String filePath = fileStorageService.storeFile(file, newFilename); // Pass the new filename to the storage service
                Image image = new Image();
                image.setPath(filePath);
                storedImages.add(image);
            } catch (IOException e) {
                throw new RuntimeException("Error storing file", e);
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
