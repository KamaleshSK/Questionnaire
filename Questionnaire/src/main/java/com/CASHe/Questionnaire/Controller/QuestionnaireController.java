package com.CASHe.Questionnaire.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CASHe.Questionnaire.Model.Questionnaire;
import com.CASHe.Questionnaire.Service.QuestionnaireService;

@RestController("/Questionnaire")
public class QuestionnaireController {
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@GetMapping("/fetch-all-questions")
	public List<Questionnaire> getAllQuestions() {
		return questionnaireService.getAllQuestions();
	}
	
	@GetMapping("/fetch-question-by-id")
	public Questionnaire getQuestionById(@RequestParam("id") String questionId) {
		return questionnaireService.getQuestionById(questionId);
	}
	
	@GetMapping("/fetch-questions-by-type")
	public List<Questionnaire> getQuestionsByType(@RequestParam("type") String questionType) {
		return questionnaireService.getQuestionsByType(questionType);
	}
	
	@GetMapping("/fetch-questions-by-topic")
	public List<Questionnaire> getQuestionsByTopic(@RequestParam("topic") String questionTopic) {
		return questionnaireService.getQuestionsByTopic(questionTopic);
	}
	
	// @PostMapping("/upload-questions")
}
