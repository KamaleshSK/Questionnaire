package com.CASHe.Questionnaire.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.Data;

@Data
@JsonPropertyOrder({ "topic" })
public class TopicDTO {
	
	@JsonProperty("topic")
	private String topic;
	
	public static TopicDTO newInstance(String jsonObject) {
		TopicDTO topic = null;
		try {
			ObjectMapper mapper = JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
			topic = mapper.readValue(jsonObject, TopicDTO.class);
		} catch (Exception e) {
			System.out.println("#### Exception on Employee Object Creation from json ####");
			e.printStackTrace();
		}
		return topic;
	}
	
}
