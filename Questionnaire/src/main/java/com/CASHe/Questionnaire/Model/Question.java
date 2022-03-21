package com.CASHe.Questionnaire.Model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity	
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
	
	@Id
	@SequenceGenerator(name = "question_sequence", sequenceName = "question_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_sequence")
	private Short questionId;
	
	@Column(length = 1000)
	private String questionContent;
	
	@Column(length = 1000)
	private String description;
	
	@Column(length = 50)
	private String questionType;
	
	private boolean isActive;
	
	private Date createdDate;
	
	@Column(length = 50)
	private String createdBy;
	
	@ManyToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "questionnaire_id",
			referencedColumnName = "questionnaireId"
	)
	private Questionnaire questionnaireId;
	
	@OneToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY,
			mappedBy = "questionId"
	)
	@JsonIgnoreProperties({"questionId", "hibernateLazyInitializer"})
	private Answer answer;
	
}
