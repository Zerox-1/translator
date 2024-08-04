package com.example.Translator.controller;

import com.example.Translator.model.TranslationRequest;
import com.example.Translator.model.TranslationRequestEntity;
import com.example.Translator.repository.TranslationRequestRepository;
import com.example.Translator.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TranslationController {
    @Autowired
    private TranslationService translationService;

    @Autowired
    private TranslationRequestRepository translationRequestRepository;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody TranslationRequest request, HttpServletRequest httpRequest) {
        // Логирование для отладки
        System.out.println("Received request: " + request);

        // Проверка на пустые поля
        if (request.getText() == null || request.getText().isEmpty()) {
            return ResponseEntity.badRequest().body("Text cannot be empty");
        }

        try {
            // Выполнение перевода
            String translatedText = translationService.translateText(request.getText(), request.getSourceLang(), request.getTargetLang());

            // Сохранение запроса в базу данных
            TranslationRequestEntity entity = new TranslationRequestEntity();
            entity.setIpAddress(httpRequest.getRemoteAddr());
            entity.setSourceText(request.getText());
            entity.setTranslatedText(translatedText);
            entity.setRequestTime(LocalDateTime.now());
            translationRequestRepository.save(entity);

            // Возврат ответа
            return ResponseEntity.ok(translatedText);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не найден язык исходного сообщения");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка доступа к ресурсу перевода");
        }
    }
}