package com.api.demo.repository;

import com.api.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.demo.entity.ManagerReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerReviewRepository extends JpaRepository<ManagerReview, Long> {

    List<ManagerReview> findByEmployeeId(Long employeeId);
}

