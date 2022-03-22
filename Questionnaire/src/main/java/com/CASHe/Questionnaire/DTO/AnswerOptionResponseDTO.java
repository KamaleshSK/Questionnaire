package com.CASHe.Questionnaire.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerOptionResponseDTO {
	
	@JsonProperty("option_id")
	private Short optionId;
	
	@JsonProperty("option_content")
	private String optionContent;
	
	@JsonProperty("description")
	private String description;
	
}
