package com.api.demo.repository;

import com.api.demo.entity.Employee;
import com.api.demo.entity.ManagerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.demo.entity.SelfReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelfReviewRepository extends JpaRepository<SelfReview,Long> {

    List<SelfReview> findByEmployeeId(Long employeeId);
}
