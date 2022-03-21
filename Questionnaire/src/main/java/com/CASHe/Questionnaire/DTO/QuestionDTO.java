package com.CASHe.Questionnaire.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "question_content", "question_type", "created_by", "answer" })
public class QuestionDTO {

	@JsonProperty("question_content")
	private String questionContent;
	
	@JsonProperty("question_type")
	private String questionType;
	
	@JsonProperty("created_by")
	private String createdBy;
	
	@JsonProperty("answer")
	private AnswerDTO answer;
	
}
