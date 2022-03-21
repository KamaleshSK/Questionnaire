package com.CASHe.Questionnaire.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CASHe.Questionnaire.DTO.AnswerDTO;
import com.CASHe.Questionnaire.DTO.AnswerOptionDTO;
import com.CASHe.Questionnaire.DTO.QuestionDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireListUploadDTO;
import com.CASHe.Questionnaire.Model.Answer;
import com.CASHe.Questionnaire.Model.AnswerOption;
import com.CASHe.Questionnaire.Model.Question;
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
	
	/*
	 * Save questionnaires to db
	 */
	public void saveQuestionnaireList(String jsonObject) {
		List<QuestionnaireDTO> questionnaireList = QuestionnaireListUploadDTO.newInstance(jsonObject).getQuestionnaireList();
		
		for (QuestionnaireDTO questionnaire : questionnaireList) {
			
			Questionnaire questionnaireSave = Questionnaire.builder()
					.questionnaireTopic(questionnaire.getQuestionnaireTopic())
					.description(questionnaire.getDescription())
					.createdBy(questionnaire.getCreatedBy())
					.createdDate(new Date(System.currentTimeMillis()))
					.isActive(true)
					.build();
			
			List<QuestionDTO> questionList = questionnaire.getQuestionList();
			for (QuestionDTO question : questionList) {
				
				Question questionSave = Question.builder()
						.isActive(true)
						.questionnaireId(questionnaireSave)
						.questionContent(question.getQuestionContent())
						.questionType(question.getQuestionType())
						.createdBy(question.getCreatedBy())
						.createdDate(new Date(System.currentTimeMillis()))
						.build();
				
				AnswerDTO answer = question.getAnswer();
				List<AnswerOptionDTO> optionList = answer.getOptions();
				Answer answerSave = Answer.builder()
						.answerType(answer.getAnswerType())
						.questionId(questionSave)
						.createdBy(answer.getCreatedBy())
						.createdDate(new Date(System.currentTimeMillis()))
						.isActive(true)
						.build();
				
				for (AnswerOptionDTO option : optionList) {

					AnswerOption answerOption = AnswerOption.builder()
							.answerId(answerSave)
							.optionContent(option.getOptionContent())
							.createdBy(option.getCreatedBy())
							.createdDate(new Date(System.currentTimeMillis()))
							.isActive(true)
							.build();
					
					if (answer.getCorrectOption()-1 == optionList.indexOf(option)) {
						answerSave.setCorrectOption(answerOption);
					}
					
					answerOptionRepository.save(answerOption);
				}
			}
		}
	}
	
	/*
	 * Fetch all Questionnaires (without children)
	 */
	public List<Questionnaire> findAllQuestionnairesLazy() {
		return questionnaireRepository.findAll();
	}
	
	/*
	 * Fetch all Questionnaire that are active (without children)
	 */
	public List<Questionnaire> findAllActiveQuestionnairesLazy() {
		return questionnaireRepository.findAllByIsActive(true);
	}
	
	/*
	 * Fetch all Questionnaires (with children)
	 */
	public List<Questionnaire> findAllQuestionnairesEager() {
		
		List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
		
		for (Questionnaire questionnaire : questionnaireList) {
			List<Question> questionsList = questionnaire.getQuestions();

			for (Question question : questionsList) {
				Answer answer = question.getAnswer();
				
				answer.setAnswerOptions(answer.getAnswerOptions());
				question.setAnswer(answer);
			}
			
			questionnaire.setQuestions(questionsList);
		}
		
		return questionnaireList;
	}
	
	/*
	 *  Fetch all Active Questionnaires (with children) 
	 */
	public List<Questionnaire> findAllActiveQuestionnairesEager() {
		
		List<Questionnaire> questionnaireList = questionnaireRepository.findAllByIsActive(true);
		
		for (Questionnaire questionnaire : questionnaireList) {
			Short questionnaireId = questionnaire.getQuestionnaireId();
			List<Question> questionsList = questionRepository.findAllByQuestionnaireIdAndIsActive(questionnaireId, true);
			
			for (Question question : questionsList) {
				Answer answer = question.getAnswer();
				Short answerId = answer.getAnswerId();
				
				answer.setAnswerOptions(answerOptionRepository.findAllByAnswerIdAndIsActive(answerId, true));
			}
			
			questionnaire.setQuestions(questionsList);
		}
		
		return questionnaireList;
	}
	
	/*
	 * by topic
	 */
	
	/*
	 * by type
	 */
	
	/*
	 * by topic and type
	 */
} 
