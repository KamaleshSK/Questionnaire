package com.CASHe.Questionnaire.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity	
@Table(name = "questionnaire_primary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questionnaire {
	
	@Id
	@SequenceGenerator(name = "question_sequence", sequenceName = "question_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_sequence")
	private Short questionId;
	
	@Column(length = 1000)
	private String question;
	
	@Column(length = 500)
	private String option1;
	
	@Column(length = 500)
	private String option2;
	
	private Short questionType;
	
	private Short questionTopic;
	
}
