package com.example.Translator.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class TranslationRequest {

    @NotBlank
    @JsonProperty("text")
    private String text;

    @NotBlank
    @JsonProperty("sourceLang")
    private String sourceLang;

    @NotBlank
    @JsonProperty("targetLang")
    private String targetLang;

    // Getters and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }
}
