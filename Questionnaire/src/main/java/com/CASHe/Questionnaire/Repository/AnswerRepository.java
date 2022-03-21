package com.CASHe.Questionnaire.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CASHe.Questionnaire.Model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Short> {

	List<Answer> findAll();
	
	/*
	 * Fetches all active or inactive Answers only depending upon the isActive parameter.
	 */
	List<Answer> findAllByIsActive(boolean isActive);
	
	Optional<Answer> findById(Short id);
	
	List<Answer> findAllByAnswerType(String answerType);
	
	/*
	 * Fetches answer for a particular question by question_id
	 */
	@Query(
			value = "SELECT * FROM answer WHERE question_id = :questionId ;",
			nativeQuery = true
	)
	Answer findAnswerByQuestionId(@Param("questionId") Short questionId);
}
