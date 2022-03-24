package com.CASHe.Questionnaire.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.CASHe.Questionnaire.DTO.AnswerDTO;
import com.CASHe.Questionnaire.DTO.AnswerOptionDTO;
import com.CASHe.Questionnaire.DTO.AnswerOptionResponseDTO;
import com.CASHe.Questionnaire.DTO.AnswerResponseDTO;
import com.CASHe.Questionnaire.DTO.QuestionDTO;
import com.CASHe.Questionnaire.DTO.QuestionResponseDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireListUploadDTO;
import com.CASHe.Questionnaire.DTO.QuestionnaireResponseDTO;
import com.CASHe.Questionnaire.DTO.TopicAndTypeDTO;
import com.CASHe.Questionnaire.DTO.TopicDTO;
import com.CASHe.Questionnaire.Model.Answer;
import com.CASHe.Questionnaire.Model.AnswerOption;
import com.CASHe.Questionnaire.Model.Question;
import com.CASHe.Questionnaire.Model.Questionnaire;
import com.CASHe.Questionnaire.Repository.AnswerOptionRepository;
import com.CASHe.Questionnaire.Repository.AnswerRepository;
import com.CASHe.Questionnaire.Repository.QuestionRepository;
import com.CASHe.Questionnaire.Repository.QuestionnaireRepository;
import com.CASHe.Questionnaire.Utils.CSVParser;

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
	 * fetches Questionnaire by topic if already present
	 * else builds new Questionnaire using information from the csvColumnFields
	 */
	private Questionnaire fetchQuestionnaireIfPresentElseBuildNew(String questionnaireTopic, List<String> csvColumnFields) {
		
		Questionnaire questionnaire = questionnaireRepository.findByQuestionnaireTopicAndIsActiveTrue(questionnaireTopic);
		if (questionnaire != null) {
			return questionnaire;
		}
		
		questionnaire = Questionnaire.builder()
				.questionnaireTopic(csvColumnFields.get(5))
				.description(csvColumnFields.get(7))
				.createdBy(csvColumnFields.get(6))
				.createdDate(new Date(System.currentTimeMillis()))
				.isActive(true)
				.build();
		return questionnaire;
	}

	
	/*
	 * Save questionnaires to db after parsing csv file
	 */
	public void saveQuestionnaireListFromCSVFile(MultipartFile file) throws IOException {
		
		BufferedReader csvReader = null;
		csvReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		
		String csvRow;
		String prevQuestionnaireTopic = null;
		Questionnaire questionnaireSave = null;
		while ((csvRow = csvReader.readLine()) != null) {
			
			/*
			 * PSV columns in the order:
			 * 
			 * question_content (0) | option_1 (1) | option_2 (2) | correct_option (3) | question_type (4) |
			 * questionnaire_topic (5) | created_by (6) | questionnaire_description (7)
			 *
			 * csv parser: refer to function comments for parsing details
			 */
			List<String> columnFields = CSVParser.csvParser(csvRow);
			
			/*
			 * Checking if questionnaireTopic already exists in the db
			 * if YES: fetch the questionnaire and add questions to it
			 * if NO: create new questionnaire and add questions to it
			 */
			String questionnaireTopic = columnFields.get(5);
			if (prevQuestionnaireTopic == null || !prevQuestionnaireTopic.equalsIgnoreCase(questionnaireTopic)) {
				prevQuestionnaireTopic = new String(questionnaireTopic);
				// if there is a change in the questionnaire topic or starting 
				// check and fetch Questionnaire if already present
				questionnaireSave = fetchQuestionnaireIfPresentElseBuildNew(questionnaireTopic, columnFields);
			}
			
			Question questionSave = Question.builder()
					.isActive(true)
					.questionnaireId(questionnaireSave)
					.questionContent(columnFields.get(0))
					.questionType(columnFields.get(4))
					.createdBy(columnFields.get(6))
					.createdDate(new Date(System.currentTimeMillis()))
					.build();
			
			Answer answerSave = Answer.builder()
					.answerType(columnFields.get(4))
					.questionId(questionSave)
					.createdBy(columnFields.get(6))
					.createdDate(new Date(System.currentTimeMillis()))
					.isActive(true)
					.build();
			
			AnswerOption option1Save = AnswerOption.builder()
					.answerId(answerSave)
					.optionContent(columnFields.get(1))
					.createdBy(columnFields.get(6))
					.createdDate(new Date(System.currentTimeMillis()))
					.isActive(true)
					.build();
			
			AnswerOption option2Save = AnswerOption.builder()
					.answerId(answerSave)
					.optionContent(columnFields.get(2))
					.createdBy(columnFields.get(6))
					.createdDate(new Date(System.currentTimeMillis()))
					.isActive(true)
					.build();
			
			if (columnFields.get(3) == "1") {
				answerSave.setCorrectOption(option1Save);
			} else {
				answerSave.setCorrectOption(option2Save);
			}
			
			answerOptionRepository.save(option1Save);
			answerOptionRepository.save(option2Save);
		}
	}
	
	/*
	 * fetches Questionnaire by topic if already present
	 * else builds new Questionnaire using information from jsonBody
	 */
	private Questionnaire fetchQuestionnaireIfPresentElseBuildNew(String questionnaireTopic, QuestionnaireDTO questionnaireDTO) {
		
		Questionnaire questionnaireSave;
		questionnaireSave = questionnaireRepository.findByQuestionnaireTopicAndIsActiveTrue(questionnaireTopic);
		if (questionnaireSave != null) {
			return questionnaireSave;
		}
		
		questionnaireSave = Questionnaire.builder()
				.questionnaireTopic(questionnaireDTO.getQuestionnaireTopic())
				.description(questionnaireDTO.getDescription())
				.createdBy(questionnaireDTO.getCreatedBy())
				.createdDate(new Date(System.currentTimeMillis()))
				.isActive(true)
				.build();
		return questionnaireSave;
	}
	
	/*
	 * Save questionnaires to db
	 */
	public void saveQuestionnaireList(String jsonObject) {
		
		List<QuestionnaireDTO> questionnaireList = QuestionnaireListUploadDTO.newInstance(jsonObject).getQuestionnaireList();
		
		for (QuestionnaireDTO questionnaire : questionnaireList) {
			
			String questionnaireTopic = questionnaire.getQuestionnaireTopic();
			Questionnaire questionnaireSave = fetchQuestionnaireIfPresentElseBuildNew(questionnaireTopic, questionnaire);
			
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
	 * Fetch active questionnaires by topic
	 */
	public List<QuestionnaireResponseDTO> findQuestionnaireEagerByTopic(String jsonObject) {
		
		if (jsonObject == null) return null;
		
		String topic = TopicDTO.newInstance(jsonObject).getTopic();
		List<Questionnaire> activeQuestionnaireList = questionnaireRepository.findAllByQuestionnaireTopicAndIsActiveTrue(topic);
		List<QuestionnaireResponseDTO> questionnaireResponseList = constructQuestionnaireResponseList(activeQuestionnaireList, true /* isEager */);
		return questionnaireResponseList;
	}
	
	/*
	 *  Fetch active questions (inside questionnaire) by topic and question_type
	 */
	public List<QuestionnaireResponseDTO> findQuestionsByTopicAndType(String jsonObject) {
		
		if (jsonObject == null) return null;
		
		TopicAndTypeDTO topicAndType = TopicAndTypeDTO.newInstance(jsonObject);
		List<Questionnaire> questionnaireList = questionnaireRepository.findAllByQuestionnaireTopicAndIsActiveTrue(topicAndType.getTopic());
		List<QuestionnaireResponseDTO> questionnaireResponseList = new ArrayList<>();
		for (Questionnaire questionnaire : questionnaireList) {
			
			List<Question> questionList = questionRepository.findAllByQuestionnaireIdAndQuestionTypeAndIsActiveTrue(questionnaire.getQuestionnaireId(), topicAndType.getType());
			List<QuestionResponseDTO> questionResponseList = constructQuestionResponseList(questionList, true /* isEager */);
			
			QuestionnaireResponseDTO questionnaireResponse = constructQuestionnaireResponse(questionnaire, questionResponseList);	
			questionnaireResponseList.add(questionnaireResponse);
		}
		
		return questionnaireResponseList;
	}
	
	/*
	 * Delete Questionnaire by Id (lazy -> does not cascade)
	 */
	public void deleteQuestionnaireLazyById(String questionnaireId) {
		
		if (questionnaireId == null) return;
		questionnaireRepository.deleteById(Short.valueOf(questionnaireId));
	}
	
	/*
	 * Delete Question by Id (lazy -> does not cascade)
	 */
	public void deleteQuestionLazyById(String questionId) {
		
		if (questionId == null) return;
		questionRepository.deleteById(Short.valueOf(questionId));
	}
	
	/*
	 * Delete Answer by Id (lazy -> does not cascade)
	 */
	public void deleteAnswerLazyById(String answerId) {
		
		if (answerId == null) return;
		answerRepository.deleteById(Short.valueOf(answerId));
	}
	
	/*
	 * Delete AnswerOption by Id (lazy -> does not cascade)
	 */
	public void deleteAnswerOptionLazyById(String answerOptionId) {
		
		if (answerOptionId == null) return;
		answerOptionRepository.deleteById(Short.valueOf(answerOptionId));
	}
	
} 
