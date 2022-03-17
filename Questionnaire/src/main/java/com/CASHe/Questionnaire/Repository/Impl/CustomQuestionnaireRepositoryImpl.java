package com.CASHe.Questionnaire.Repository.Impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.CASHe.Questionnaire.Repository.CustomQuestionnaireRepository;

public class CustomQuestionnaireRepositoryImpl implements CustomQuestionnaireRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
}
