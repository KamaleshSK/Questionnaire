package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerResponseDTO {
	
	@JsonProperty("answer_id")
	public Short answerId;
	
	@JsonProperty("description")
	public String description;
	
	@JsonProperty("answer_type")
	public String answerType;
	
	@JsonProperty("correct_option")
	public Short correctOption;
	
	@JsonProperty("options")
	public List<AnswerOptionResponseDTO> options = null;
	
}
