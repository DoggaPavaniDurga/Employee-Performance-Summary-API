package com.api.demo.controller;

import com.api.demo.dto.PerformanceSummaryDto;
import com.api.demo.entity.Employee;
import com.api.demo.entity.ManagerReview;
import com.api.demo.entity.SelfReview;
import com.api.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testSubmitSelfReview() throws Exception {
        SelfReview selfReview = new SelfReview();
        selfReview.setReviewText("Review text");
        doNothing().when(employeeService).submitSelfReview(1L, selfReview);

        mockMvc.perform(post("/employees/1/self-review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reviewText\":\"Review text\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Self-review submitted successfully."));
    }

    @Test
    void testSubmitManagerReview() throws Exception {
        ManagerReview managerReview = new ManagerReview();
        managerReview.setReviewText("Manager Review text");
        managerReview.setRating(4);
        doNothing().when(employeeService).submitManagerReview(1L, managerReview);

        mockMvc.perform(post("/employees/1/manager-review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reviewText\":\"Manager Review text\",\"rating\":4}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Manager review submitted successfully."));
    }

    @Test
    void testGetPerformanceSummary() throws Exception {
        PerformanceSummaryDto performanceSummary = new PerformanceSummaryDto(
                "Self Review Text", java.time.LocalDateTime.now(),
                "Manager Review Text", 4, java.time.LocalDateTime.now(), 3.5);

        when(employeeService.getPerformanceSummary(1L)).thenReturn(performanceSummary);

        mockMvc.perform(get("/employees/1/performance-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.selfReviewText").value("Self Review Text"))
                .andExpect(jsonPath("$.managerRating").value(4));
    }
}
