package com.example.Translator.repository;

import com.example.Translator.model.TranslationRequest;
import com.example.Translator.model.TranslationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRequestRepository extends JpaRepository<TranslationRequestEntity, Long> {
}
