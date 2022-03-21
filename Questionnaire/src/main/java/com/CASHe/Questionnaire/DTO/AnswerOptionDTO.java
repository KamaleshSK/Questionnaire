package com.CASHe.Questionnaire.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "option_content", "created_by" })
public class AnswerOptionDTO {
	
	@JsonProperty("option_content")
	private String optionContent;
	
	@JsonProperty("created_by")
	private String createdBy;
	
}
