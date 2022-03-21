package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "answer_type", "created_by", "correct_option", "options" })
public class AnswerDTO {
	
	@JsonProperty("answer_type")
	private String answerType;
	
	@JsonProperty("created_by")
	private String createdBy;
	
	@JsonProperty("correct_option")
	private Short correctOption;
	
	@JsonProperty("options")
	private List<AnswerOptionDTO> options = null;
	
}
