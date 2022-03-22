package com.CASHe.Questionnaire.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CASHe.Questionnaire.DTO.QuestionnaireResponseDTO;
import com.CASHe.Questionnaire.Model.Questionnaire;
import com.CASHe.Questionnaire.Service.QuestionnaireService;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@GetMapping("/get-all-questionnaires-lazy")
	public List<QuestionnaireResponseDTO> getAllQuestionnairesLazy() {
		return questionnaireService.findAllQuestionnairesLazy();
	}
	
	@GetMapping("/get-all-active-questionnaires-lazy")
	public List<QuestionnaireResponseDTO> getAllActiveQuestionnairesLazy() {
		return questionnaireService.findAllActiveQuestionnairesLazy();
	}
	
	@GetMapping("/get-all-questionnaires-eager")
	public List<QuestionnaireResponseDTO> getAllQuestionnairesEager() {
		return questionnaireService.findAllQuestionnairesEager();
	}
	
	@GetMapping("/get-all-active-questionnaires-eager")
	public List<QuestionnaireResponseDTO> getAllActiveQuestionnairesEager() {
		return questionnaireService.findAllActiveQuestionnairesEager();
	}
	
	@PostMapping("/upload-questionnaire-list")
	public void uploadQuestionnaireList(@RequestBody String jsonObject) {
		questionnaireService.saveQuestionnaireList(jsonObject);
	}
}
