package com.CASHe.Questionnaire.Repository;

import org.springframework.stereotype.Repository;

import com.CASHe.Questionnaire.Model.Questionnaire;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Short>, CustomQuestionnaireRepository {
	
	List<Questionnaire> findAll();
	
	Optional<Questionnaire> findById(Short id);
	
	List<Questionnaire> findAllByQuestionType(Short questionType);
	
	List<Questionnaire> findAllByQuestionTopic(Short questionTopic);
	
}
