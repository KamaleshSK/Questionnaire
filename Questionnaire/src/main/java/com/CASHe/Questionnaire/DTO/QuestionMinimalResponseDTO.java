package com.CASHe.Questionnaire.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "content", "correct_option", "option_list" })
@Builder
public class QuestionMinimalResponseDTO {

	@JsonProperty("id")
	private Short id;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("correct_option")
	private Short correctOption;
	
	@JsonProperty("option_list")
	private List<String> optionList = null;
	
}
