package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.Data;

@Data
@JsonPropertyOrder({ "employee_id", "department_name", "score" })
public class EmployeeScoreDTO {
	
	@JsonProperty("employee_id")
	private Short employeeId;
	
	@JsonProperty("department_name")
	private String departmentName;
	
	@JsonProperty("score")
	private Short score;
	
	public static EmployeeScoreDTO newInstance(String jsonObject) {
		EmployeeScoreDTO employeeScore = null;
		try {
			ObjectMapper mapper = JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
			employeeScore = mapper.readValue(jsonObject, EmployeeScoreDTO.class);
		} catch (Exception e) {
			System.out.println("#### Exception on Employee Object Creation from json ####");
			e.printStackTrace();
		}
		return employeeScore;
	}
	
}
