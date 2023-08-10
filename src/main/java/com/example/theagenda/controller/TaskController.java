package com.example.theagenda.controller;

import com.example.theagenda.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tasks/v1/")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;
}
