package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.CASHe.Questionnaire.Model.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionnaireResponseDTO {
	
	@JsonProperty("questionnaire_id")
	private Short questionnaireId;
	
	@JsonProperty("topic")
	private String questionnaireTopic;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("questions")
	private List<QuestionResponseDTO> questions = null;

}
