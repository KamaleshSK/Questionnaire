package com.CASHe.Questionnaire.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CASHe.Questionnaire.Model.AnswerOption;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Short> {

	List<AnswerOption> findAll();
	
	/*
	 * Fetches all active or inactive Answer Options only depending upon the isActive parameter.
	 */
	List<AnswerOption> findAllByIsActive(boolean isActive);
	
	Optional<AnswerOption> findById(Short id);
	
	/*
	 * Fetches all answer options belonging to a particular answer by id
	 */
	@Query(
			value = "SELECT * FROM answer_option WHERE answer_id = :answerId ;",
			nativeQuery = true
	)
	List<AnswerOption> findAllByAnswerId(@Param("answerId") Short answerId);
	
	/*
	 * Fetches all active answer options belonging to a particular answer by id
	 */
	@Query(
			value = "SELECT * FROM answer_option WHERE answer_id = :answerId AND is_active = :isActive ;",
			nativeQuery = true
	)
	List<AnswerOption> findAllByAnswerIdAndIsActive(@Param("answerId") Short answerId, @Param("isActive") boolean isActive);
}
