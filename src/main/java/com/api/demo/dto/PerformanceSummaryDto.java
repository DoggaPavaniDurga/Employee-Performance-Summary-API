package com.api.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data


public class PerformanceSummaryDto {

    private String selfReviewText;
    private LocalDateTime selfReviewTimestamp;
    private String managerReviewText;
    private Integer managerRating;
    private LocalDateTime managerReviewTimestamp;
    private double performanceScore;

    public PerformanceSummaryDto(String selfReviewText, LocalDateTime selfReviewTimestamp,
                                 String managerReviewText, Integer managerRating,
                                 LocalDateTime managerReviewTimestamp, double performanceScore) {
        this.selfReviewText = selfReviewText;
        this.selfReviewTimestamp = selfReviewTimestamp;
        this.managerReviewText = managerReviewText;
        this.managerRating = managerRating;
        this.managerReviewTimestamp = managerReviewTimestamp;
        this.performanceScore = performanceScore;
    }

    // Getters and Setters
}
