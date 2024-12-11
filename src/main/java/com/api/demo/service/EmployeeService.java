package com.api.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.demo.dto.PerformanceSummaryDto;
import com.api.demo.entity.Employee;
import com.api.demo.entity.ManagerReview;
import com.api.demo.entity.SelfReview;
import com.api.demo.repository.EmployeeRepository;
import com.api.demo.repository.ManagerReviewRepository;
import com.api.demo.repository.SelfReviewRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SelfReviewRepository selfReviewRepository;

    @Autowired
    ManagerReviewRepository managerReviewRepository;


    public PerformanceSummaryDto getPerformanceSummary(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<SelfReview> selfReviews = selfReviewRepository.findByEmployeeId(employee.getId());
        List<ManagerReview> managerReviews = managerReviewRepository.findByEmployeeId(employee.getId());

        // Assuming latest reviews are required
        SelfReview selfReview = selfReviews.isEmpty() ? null : selfReviews.get(selfReviews.size() - 1);
        ManagerReview managerReview = managerReviews.isEmpty() ? null : managerReviews.get(managerReviews.size() - 1);

        if (selfReview == null || managerReview == null) {
            throw new RuntimeException("Review data is missing for the employee.");
        }

        double performanceScore = calculatePerformanceScore(selfReview, managerReview);

        return new PerformanceSummaryDto(
                selfReview.getReviewText(),
                selfReview.getSubmittedAt(),
                managerReview.getReviewText(),
                managerReview.getRating(),
                managerReview.getSubmittedAt(),
                performanceScore
        );
    }


    private double calculatePerformanceScore(SelfReview selfReview, ManagerReview managerReview) {
        int selfReviewScore = selfReview.getReviewText().length() < 50 ? 1 :
                selfReview.getReviewText().length() <= 150 ? 2 : 3;

        double managerWeightedScore = managerReview.getRating() * 0.7;

        return (selfReviewScore + managerWeightedScore) / 2;
    }




    public void submitSelfReview(Long employeeId, SelfReview selfReview) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        selfReview.setEmployee(employee);
        selfReview.setSubmittedAt(java.time.LocalDateTime.now());
        selfReviewRepository.save(selfReview);
    }


    public void submitManagerReview(Long employeeId, ManagerReview managerReview) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        managerReview.setEmployee(employee);
        managerReview.setSubmittedAt(java.time.LocalDateTime.now());
        managerReviewRepository.save(managerReview);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
