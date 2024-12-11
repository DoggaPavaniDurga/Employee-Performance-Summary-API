package com.api.demo.entity;

import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String department;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SelfReview> selfReviews;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ManagerReview> managerReviews;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<SelfReview> getSelfReviews() {
		return selfReviews;
	}

	public void setSelfReviews(List<SelfReview> selfReviews) {
		this.selfReviews = selfReviews;
	}

	public List<ManagerReview> getManagerReviews() {
		return managerReviews;
	}

	public void setManagerReviews(List<ManagerReview> managerReviews) {
		this.managerReviews = managerReviews;
	}
    
    


}
