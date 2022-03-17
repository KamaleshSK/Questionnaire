package com.CASHe.Questionnaire.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CASHe.Questionnaire.Model.Question;

public interface QuestionRepository extends JpaRepository<Question, Short> {

}
