package com.api.demo.controller;

import com.api.demo.entity.Employee;
import com.api.demo.entity.ManagerReview;
import com.api.demo.entity.SelfReview;
import com.api.demo.repository.EmployeeRepository;
import com.api.demo.repository.ManagerReviewRepository;
import com.api.demo.repository.SelfReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.demo.dto.PerformanceSummaryDto;
import com.api.demo.service.EmployeeService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/{id}/self-review")
    public ResponseEntity<String> submitSelfReview(
            @PathVariable("id") Long employeeId,
            @RequestBody SelfReview selfReview) {
        employeeService.submitSelfReview(employeeId, selfReview);
        return ResponseEntity.ok("Self-review submitted successfully.");
    }

    @PostMapping("/{id}/manager-review")
    public ResponseEntity<String> submitManagerReview(
            @PathVariable("id") Long employeeId,
            @RequestBody ManagerReview managerReview) {
        if (managerReview.getRating() < 1 || managerReview.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5.");
        }
        employeeService.submitManagerReview(employeeId, managerReview);
        return ResponseEntity.ok("Manager review submitted successfully.");
    }

    @GetMapping("/{id}/performance-summary")
    public ResponseEntity<PerformanceSummaryDto> getPerformanceSummary(@PathVariable Long id) {
        PerformanceSummaryDto performanceSummary = employeeService.getPerformanceSummary(id);
        return ResponseEntity.ok(performanceSummary);
    }

    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }
}