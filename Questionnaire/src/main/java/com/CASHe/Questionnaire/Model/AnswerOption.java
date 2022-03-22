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
@Table(name = "answer_option")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOption {

	@Id
	@SequenceGenerator(name = "option_sequence", sequenceName = "option_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_sequence")
	private Short optionId;
	
	@Column(length = 500)
	private String optionContent;
	
	@Column(length = 1000)
	private String description;
	
	private boolean isActive;
	
	private Date createdDate;
	
	@Column(length = 50)
	private String createdBy;
	
	@ManyToOne( 
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	@JoinColumn( 
			name = "answer_id",
			referencedColumnName = "answerId"
	)
	@JsonIgnoreProperties({"correctOption", "hibernateLazyInitializer"})
	private Answer answerId;
	
	public Short getOptionId() {
		return optionId;
	}

	public void setOptionId(Short optionId) {
		this.optionId = optionId;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
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

	public Answer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Answer answerId) {
		this.answerId = answerId;
	}
}
