package com.petshop.web.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.shared.dto.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalViewExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", resolveMessage(ex));
        return "error/500";
    }

    private String resolveMessage(Exception ex) {
        if (ex instanceof RestClientResponseException restEx) {
            try {
                ApiErrorResponse error = objectMapper.readValue(restEx.getResponseBodyAsString(), ApiErrorResponse.class);
                if (error.details() != null && !error.details().isEmpty()) {
                    return error.message() + " " + String.join(" ", error.details());
                }
                return error.message();
            } catch (Exception ignored) {
                return restEx.getStatusText();
            }
        }
        return ex.getMessage();
    }
}
