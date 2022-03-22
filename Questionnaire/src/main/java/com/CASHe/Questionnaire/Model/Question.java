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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity	
@Table(name = "question")
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

	public Short getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Short questionId) {
		this.questionId = questionId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Questionnaire getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Questionnaire questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
}
