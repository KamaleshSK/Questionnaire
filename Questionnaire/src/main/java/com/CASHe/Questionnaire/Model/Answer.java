package com.CASHe.Questionnaire.Model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
	
	@Id
	@SequenceGenerator(name = "answer_sequence", sequenceName = "answer_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_sequence")
	private Short answerId;
	
	private boolean isActive;
	
	@Column(length = 1000)
	private String description;
	
	private Date createdDate;
	
	@Column(length = 50)
	private String createdBy;
	
	@Column(length = 50)
	private String answerType;
	
	@OneToOne(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "question_id",
			referencedColumnName = "questionId"
	)
	private Question questionId;
	
	@OneToOne( 
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	@JoinColumn( 
			name = "correct_option",
			referencedColumnName = "optionId"
	)
	@JsonIgnoreProperties({"answerId", "hibernateLazyInitializer"})
	private AnswerOption correctOption;
	
	@OneToMany(
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY,
			mappedBy = "answerId"
	)
	@JsonIgnoreProperties({"answerId", "hibernateLazyInitializer"})
	private List<AnswerOption> answerOptions;

	public Short getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Short answerId) {
		this.answerId = answerId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	public AnswerOption getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(AnswerOption correctOption) {
		this.correctOption = correctOption;
	}

	public List<AnswerOption> getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(List<AnswerOption> answerOptions) {
		this.answerOptions = answerOptions;
	}
	
}
