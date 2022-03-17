package com.CASHe.Questionnaire.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CASHe.Questionnaire.Model.Questionnaire;
import com.CASHe.Questionnaire.Repository.AnswerOptionRepository;
import com.CASHe.Questionnaire.Repository.AnswerRepository;
import com.CASHe.Questionnaire.Repository.QuestionRepository;
import com.CASHe.Questionnaire.Repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {
	
	@Autowired
	private QuestionnaireRepository questionnaireRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private AnswerOptionRepository answerOptionRepository;
	
	
}
