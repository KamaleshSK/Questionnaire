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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity	
@Table(name = "answer_option")
@Data
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
	private Answer answerId;
}
