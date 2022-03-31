package com.CASHe.Questionnaire.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CASHe.Questionnaire.Model.EmployeeScore;

@Repository
public interface EmployeeScoreRepository extends JpaRepository<EmployeeScore, Short> {
		
	List<EmployeeScore> findAll();
	
}
