package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.Data;

@Data
@JsonPropertyOrder({ "questionnaires" })
public class QuestionnaireListUploadDTO {
	
	@JsonProperty("questionnaires")
	private List<QuestionnaireDTO> questionnaireList = null;
	
	public static QuestionnaireListUploadDTO newInstance(String jsonObject) {
		QuestionnaireListUploadDTO questionnaireList = null;
		try {
			ObjectMapper mapper = JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
			questionnaireList = mapper.readValue(jsonObject, QuestionnaireListUploadDTO.class);
		} catch (Exception e) {
			System.out.println("#### Exception on Employee Object Creation from json ####");
			e.printStackTrace();
		}
		return questionnaireList;
	}
	
}
