package com.api.demo.service;

import com.api.demo.entity.Employee;
import com.api.demo.entity.ManagerReview;
import com.api.demo.entity.SelfReview;
import com.api.demo.repository.EmployeeRepository;
import com.api.demo.repository.ManagerReviewRepository;
import com.api.demo.repository.SelfReviewRepository;
import com.api.demo.dto.PerformanceSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SelfReviewRepository selfReviewRepository;

    @Mock
    private ManagerReviewRepository managerReviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPerformanceSummary() {
        Employee employee = new Employee();
        employee.setId(1L);

        SelfReview selfReview = new SelfReview();
        selfReview.setReviewText("Self Review Text");
        selfReview.setSubmittedAt(java.time.LocalDateTime.now());

        ManagerReview managerReview = new ManagerReview();
        managerReview.setReviewText("Manager Review Text");
        managerReview.setRating(4);
        managerReview.setSubmittedAt(java.time.LocalDateTime.now());

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));
        when(selfReviewRepository.findByEmployeeId(1L)).thenReturn(List.of(selfReview));
        when(managerReviewRepository.findByEmployeeId(1L)).thenReturn(List.of(managerReview));

        PerformanceSummaryDto performanceSummary = employeeService.getPerformanceSummary(1L);

        assertNotNull(performanceSummary);
        assertEquals("Self Review Text", performanceSummary.getSelfReviewText());
        assertEquals(4, performanceSummary.getManagerRating());
    }

    @Test
    void testSubmitSelfReview() {
        Employee employee = new Employee();
        employee.setId(1L);
        SelfReview selfReview = new SelfReview();
        selfReview.setReviewText("Review text");

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));

        employeeService.submitSelfReview(1L, selfReview);

        verify(selfReviewRepository, times(1)).save(selfReview);
    }

    @Test
    void testSubmitManagerReview() {
        Employee employee = new Employee();
        employee.setId(1L);
        ManagerReview managerReview = new ManagerReview();
        managerReview.setReviewText("Manager review text");
        managerReview.setRating(5);

        when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));

        employeeService.submitManagerReview(1L, managerReview);

        verify(managerReviewRepository, times(1)).save(managerReview);
    }
}
