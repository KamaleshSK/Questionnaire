package com.CASHe.Questionnaire.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CASHe.Questionnaire.Model.Answer;
import com.CASHe.Questionnaire.Model.Question;
import com.CASHe.Questionnaire.Model.Questionnaire;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Short> {

	List<Question> findAll();
	
	/*
	 * Fetches all active or inactive questions only depending upon the isActive parameter.
	 */
	List<Question> findAllByIsActive(boolean isActive);
	
	Optional<Question> findById(Short id);
	
	List<Question> findAllByQuestionType(String questionType);
	
	/*
	 * Fetches all questions belonging to a questionnaire by questionnaireId
	 */
	@Query(
			value = "SELECT * FROM question WHERE questionnaire_id = :questionnaireId ;",
			nativeQuery = true
	)
	List<Question> findAllByQuestionnaireId(@Param("questionnaireId") Short questionnaireId);
	
	/*
	 * Fetches all active questions belonging to a questionnaire by questionnaireId
	 */
	@Query(
			value = "SELECT * FROM question WHERE questionnaire_id = :questionnaireId AND is_active = :isActive ;",
			nativeQuery = true
	)
	List<Question> findAllByQuestionnaireIdAndIsActive(@Param("questionnaireId") Short questionnaireId, @Param("isActive") boolean isActive);
	
	/*
	 * Fetches all active questions of a certain type and questionnaire_id
	 */
	@Query(
			value = "SELECT * FROM question WHERE questionnaire_id = :questionnaireId AND question_type = :questionType AND is_active = true ;",
			nativeQuery = true
	)
	List<Question> findAllByQuestionnaireIdAndQuestionTypeAndIsActiveTrue(@Param("questionnaireId") Short questionnaireId, @Param("questionType") String questionType);
	
	/*
	 * Does not cascade
	 */
	void deleteById(Short id);
}
