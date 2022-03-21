package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "questionnaire_topic", "description", "created_by", "questions"})
public class QuestionnaireDTO {
	
	@JsonProperty("questionnaire_topic")
	private String questionnaireTopic;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("created_by")
	private String createdBy;
	
	@JsonProperty("questions")
	private List<QuestionDTO> questionList = null;
}
