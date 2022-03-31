package com.CASHe.Questionnaire.Model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity	
@Table(name = "employee_score")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScore {
	
	@Id
	@SequenceGenerator(name = "score_sequence", sequenceName = "score_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_sequence")
	private Short employeeId;
	
	@Column(length = 40)
	private String DepartmentName;
	
	private Short score;

	public Short getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Short employeeId) {
		this.employeeId = employeeId;
	}

	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
	}

	public Short getScore() {
		return score;
	}

	public void setScore(Short score) {
		this.score = score;
	}
	
}
