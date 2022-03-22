package com.CASHe.Questionnaire.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CASHe.Questionnaire.DTO.AnswerDTO;
import com.CASHe.Questionnaire.DTO.AnswerOptionDTO;
import com.CASHe.Questionnaire.DTO.AnswerOptionResponseDTO;
import com.CASHe.Questionnaire.DTO.AnswerResponseDTO;
import com.CASHe.Questionnaire.DTO.QuestionDTO;
import com.CASHe.Questionnaire.DTO.QuestionResponseDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireListUploadDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireResponseDTO;
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
	
	private AnswerOptionResponseDTO constructAnswerOptionResponse(AnswerOption option) {
		
		AnswerOptionResponseDTO optionResponse = AnswerOptionResponseDTO.builder()
				.optionId(option.getOptionId())
				.optionContent(option.getOptionContent())
				.description(option.getDescription())
				.build();
		return optionResponse;
	}
	
	private AnswerResponseDTO constructAnswerResponse(Answer answer, List<AnswerOptionResponseDTO> answerOptionResponseList) {
		// index 0 -> option 1
		// index 1 -> option 2 and so on....
		Short correctOption = Short.valueOf((short) (answer.getAnswerOptions().indexOf(answer.getCorrectOption())+1));
		
		AnswerResponseDTO answerResponse = AnswerResponseDTO.builder()
				.answerId(answer.getAnswerId())
				.answerType(answer.getAnswerType())
				.description(answer.getDescription())
				.correctOption(correctOption)
				.options(answerOptionResponseList)
				.build();
		return answerResponse;
	}
	
	private QuestionResponseDTO constructQuestionResponse(Question question, AnswerResponseDTO answerResponse) {
		
		QuestionResponseDTO questionResponse = QuestionResponseDTO.builder()
				.questionId(question.getQuestionId())
				.questionContent(question.getQuestionContent())
				.questionType(question.getQuestionType())
				.description(question.getDescription())
				.answer(answerResponse)
				.build();
		return questionResponse;
	}
	
	private QuestionnaireResponseDTO constructQuestionnaireResponse(Questionnaire questionnaire, List<QuestionResponseDTO> questionResponseList) {
		
		QuestionnaireResponseDTO questionnaireResponse = QuestionnaireResponseDTO.builder()
				.questionnaireId(questionnaire.getQuestionnaireId())
				.description(questionnaire.getDescription())
				.questionnaireTopic(questionnaire.getQuestionnaireTopic())
				.questions(questionResponseList)
				.build();
		return questionnaireResponse;
	}
	
	private List<AnswerOptionResponseDTO> constructAnswerOptionReponseList(List<AnswerOption> answerOptions) {

		if (answerOptions == null) return null;
		
		List<AnswerOptionResponseDTO> optionResponseList = new ArrayList<>();
		for (AnswerOption option : answerOptions) {
			
			AnswerOptionResponseDTO optionResponse = constructAnswerOptionResponse(option);
			optionResponseList.add(optionResponse);
		}
		return optionResponseList;
	}
	
	private List<QuestionResponseDTO> constructQuestionResponseList(List<Question> questionList, boolean isEager) {
		
		if (questionList == null) return null;
		
		List<QuestionResponseDTO> questionResponseList = new ArrayList<>();
		for (Question question : questionList) {
			
			Answer answer = question.getAnswer();
			List<AnswerOption> options = null;
			if (isEager) {
				options = answer.getAnswerOptions();
			}
			
			List<AnswerOptionResponseDTO> optionResponseList = constructAnswerOptionReponseList(options);
			
			AnswerResponseDTO answerResponse = constructAnswerResponse(answer, optionResponseList);
			QuestionResponseDTO questionResponse = constructQuestionResponse(question, answerResponse);
			questionResponseList.add(questionResponse);
		}
		return questionResponseList;
	}
	
	private List<QuestionnaireResponseDTO> constructQuestionnaireResponseList(List<Questionnaire> questionnaireList, boolean isEager) {
		
		if (questionnaireList == null) return null;
		
		List<QuestionnaireResponseDTO> questionnaireResponseList = new ArrayList<>();
		for (Questionnaire questionnaire : questionnaireList) {
			
			List<Question> questionList = null;
			if (isEager) { 
				questionList = questionnaire.getQuestions();
			}
			
			List<QuestionResponseDTO> questionResponseList = constructQuestionResponseList(questionList, isEager);
			
			QuestionnaireResponseDTO questionnaireResponse = constructQuestionnaireResponse(questionnaire, questionResponseList);	
			questionnaireResponseList.add(questionnaireResponse);
		}
		return questionnaireResponseList;
	}
	
	/*
	 * Fetch all Questionnaires lazy
	 */
	public List<QuestionnaireResponseDTO> findAllQuestionnairesLazy() {
		
		List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
		List<QuestionnaireResponseDTO> questionnaireResponseList = constructQuestionnaireResponseList(questionnaireList, false /* isEager */);
		
		return questionnaireResponseList;
	}
	
	/*
	 * Fetch all Questionnaire that are active lazy
	 */
	public List<QuestionnaireResponseDTO> findAllActiveQuestionnairesLazy() {
		
		List<Questionnaire> activeQuestionnaireList = questionnaireRepository.findAllByIsActive(true);
		List<QuestionnaireResponseDTO> activeQuestionnaireResponseList = constructQuestionnaireResponseList(activeQuestionnaireList, false /* isEager */);
		
		return activeQuestionnaireResponseList;
	}
	
	/*
	 * Fetch all Questionnaires eager
	 */
	public List<QuestionnaireResponseDTO> findAllQuestionnairesEager() {
		
		List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
		List<QuestionnaireResponseDTO> questionnaireResponseList = constructQuestionnaireResponseList(questionnaireList, true /* isEager */);
		
		return questionnaireResponseList;
	}
	
	/*
	 *  Fetch all Active Questionnaires eager
	 */
	public List<QuestionnaireResponseDTO> findAllActiveQuestionnairesEager() {
		
		List<Questionnaire> activeQuestionnaireList = questionnaireRepository.findAllByIsActive(true);
		List<QuestionnaireResponseDTO> activeQuestionnaireResponseList = constructQuestionnaireResponseList(activeQuestionnaireList, true /* isEager */);
		
		return activeQuestionnaireResponseList;
	}
	
	/*
	 * Fetch active questionnaire by topic
	 */
	
	
	/*
	 * Fetch active questionnaire by type
	 */
	
	/*
	 * Fetch active questionnaire by topic and type
	 */
} 
