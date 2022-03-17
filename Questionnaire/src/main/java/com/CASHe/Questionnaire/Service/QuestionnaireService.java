package com.CASHe.Questionnaire.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CASHe.Questionnaire.Model.Questionnaire;
import com.CASHe.Questionnaire.Repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {
	
	@Autowired
	private QuestionnaireRepository questionnaireRepository;
	
	public List<Questionnaire> getAllQuestions() {
		return questionnaireRepository.findAll();
	}
	
	public Questionnaire getQuestionById(String questionId) {
		if (questionId == null) return null;
		
		Optional<Questionnaire> question = questionnaireRepository.findById(Short.valueOf(questionId));
		return question.get();
	}
	
	public List<Questionnaire> getQuestionsByType(String questionType) {
		if (questionType == null) return null;
		
		return questionnaireRepository.findAllByQuestionType(Short.valueOf(questionType));
	}
	
	public List<Questionnaire> getQuestionsByTopic(String questionTopic) {
		if (questionTopic == null) return null;
		
		return questionnaireRepository.findAllByQuestionTopic(Short.valueOf(questionTopic));
	}
}
