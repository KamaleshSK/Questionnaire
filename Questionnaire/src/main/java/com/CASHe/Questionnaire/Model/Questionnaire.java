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
import javax.persistence.OneToMany;
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
@Table(name = "questionnaire")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questionnaire {
	
	@Id
	@SequenceGenerator(name = "questionnaire_sequence", sequenceName = "questionnaire_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionnaire_sequence")
	private Short questionnaireId;
	
	@Column(length = 50)
	private String questionnaireTopic;
	
	@Column(length = 1000)
	private String description;
	
	private boolean isActive;
	
	private Date createdDate;
	
	@Column(length = 50)
	private String createdBy;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.PERSIST,
			mappedBy = "questionnaireId"
	)
	@JsonIgnoreProperties({"questionnaireId", "hibernateLazyInitializer"})
	private List<Question> questions;

	public Short getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Short questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaireTopic() {
		return questionnaireTopic;
	}

	public void setQuestionnaireTopic(String questionnaireTopic) {
		this.questionnaireTopic = questionnaireTopic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
