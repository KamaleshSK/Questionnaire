package com.CASHe.Questionnaire.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CASHe.Questionnaire.Model.AnswerOption;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Short> {

}
