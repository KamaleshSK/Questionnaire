package com.CASHe.Questionnaire.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.Data;

@Data
@JsonPropertyOrder({ "topic", "type" })
public class TopicAndTypeDTO {
	
	@JsonProperty("topic")
	private String topic;
	
	@JsonProperty("type")
	private String type;
	
	public static TopicAndTypeDTO newInstance(String jsonObject) {
		TopicAndTypeDTO topicAndType = null;
		try {
			ObjectMapper mapper = JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
			topicAndType = mapper.readValue(jsonObject, TopicAndTypeDTO.class);
		} catch (Exception e) {
			System.out.println("#### Exception on Employee Object Creation from json ####");
			e.printStackTrace();
		}
		return topicAndType;
	}
	
}
