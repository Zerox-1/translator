package com.example.Translator.service;

import com.example.Translator.model.TranslationRequestEntity;
import com.example.Translator.repository.TranslationRequestRepository;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "YOUR_GOOGLE_CLOUD_API_KEY"; // Замените на ваш ключ API

    public String translateText(String text, String sourceLang, String targetLang) throws Exception {
        String[] words = text.split("\\s+");
        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            futures.add(executorService.submit(() -> {
                String url = String.format("https://translation.googleapis.com/language/translate/v2?key=%s&q=%s&source=%s&target=%s", API_KEY, word, sourceLang, targetLang);
                String response = restTemplate.getForObject(url, String.class);
                // Extract translated text from response
                return response;
            }));
        }

        StringBuilder translatedText = new StringBuilder();
        for (Future<String> future : futures) {
            try {
                translatedText.append(future.get()).append(" ");
            } catch (Exception e) {
                e.printStackTrace();
                translatedText.append("ERROR").append(" ");
            }
        }

        return translatedText.toString().trim();
    }
}