package com.CASHe.Questionnaire.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionResponseDTO {

	@JsonProperty("question_id")
	private Short questionId;
	
	@JsonProperty("question_content")
	private String questionContent;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("question_type")
	private String questionType;
	
	@JsonProperty("answer")
	private AnswerResponseDTO answer;
	
}
