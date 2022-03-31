package com.CASHe.Questionnaire.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CASHe.Questionnaire.DTO.QuestionMinimalResponseDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireResponseDTO;
import com.CASHe.Questionnaire.Model.Questionnaire;
import com.CASHe.Questionnaire.Service.QuestionnaireService;

@RestController
@RequestMapping("/questionnaire")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionnaireController {
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@GetMapping("/all-questionnaires/lazy")
	public List<QuestionnaireResponseDTO> getAllQuestionnairesLazy() {
		return questionnaireService.findAllQuestionnairesLazy();
	}
	
	@GetMapping("/all-active-questionnaires/lazy")
	public List<QuestionnaireResponseDTO> getAllActiveQuestionnairesLazy() {
		return questionnaireService.findAllActiveQuestionnairesLazy();
	}
	
	@GetMapping("/all-questionnaires/eager")
	public List<QuestionnaireResponseDTO> getAllQuestionnairesEager() {
		return questionnaireService.findAllQuestionnairesEager();
	}
	
	@GetMapping("/all-active-questionnaires/eager")
	public List<QuestionnaireResponseDTO> getAllActiveQuestionnairesEager() {
		return questionnaireService.findAllActiveQuestionnairesEager();
	}
	
	@PostMapping("/all-active-questionnaire/topic/eager")
	public List<QuestionnaireResponseDTO> getAllActiveQuestionnairesEagerByTopic(@RequestBody String jsonObject) {
		return questionnaireService.findQuestionnaireEagerByTopic(jsonObject);
	}
	
	@PostMapping("/all-active-questionnaire/topic/type/eager")
	public List<QuestionnaireResponseDTO> getAllActvQuestnnrsEagerByTopicByType(@RequestBody String jsonObject) {
		return questionnaireService.findQuestionsByTopicAndType(jsonObject);
	}
	
	@PostMapping("/all-active-questions/topic/eager")
	public List<QuestionMinimalResponseDTO> getAllActvQuestnEagerByTopic(@RequestBody String jsonObject) {
		return questionnaireService.findQuestionsEagerByTopic(jsonObject);
	}
	
	@PostMapping("/upload-questionnaire-list")
	public void uploadQuestionnaireList(@RequestBody String jsonObject) {
		questionnaireService.saveQuestionnaireList(jsonObject);
	}
	
	@PostMapping("/upload-questionnaire-list/csv-file")
	public void uploadQuestionnaireListViaCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
		// Ideally this parsing task is delegated to an asynchronous task queue
		// For now parsing is done in the life cycle of the request itself
		questionnaireService.saveQuestionnaireListFromCSVFile(file);
	}
	
	@PostMapping("/upload-employee-quiz-score")
	public void uploadQuizScore(@RequestBody String jsonObject) {
		questionnaireService.saveEmployeeQuizScoreOnSubmit(jsonObject);
	}
	
	@DeleteMapping("/delete-questionnaire/lazy/id")
	public void deleteQuestionnaireLazyById(@RequestParam("id") String questionnaireId) {
		questionnaireService.deleteQuestionnaireLazyById(questionnaireId);
	}
	
	@DeleteMapping("/delete-question/lazy/id")
	public void deleteQuestionLazyById(@RequestParam("id") String questionId) {
		questionnaireService.deleteQuestionLazyById(questionId);
	}
	
	@DeleteMapping("/delete-answer/lazy/id")
	public void deleteAnswerLazyById(@RequestParam("id") String answerId) {
		questionnaireService.deleteAnswerLazyById(answerId);
	}
	
	@DeleteMapping("/delete-answer-option/lazy/id")
	public void deleteAnswerOptionLazyById(@RequestParam("id") String answerOptionId) {
		questionnaireService.deleteAnswerOptionLazyById(answerOptionId);
	}
	
	
}
