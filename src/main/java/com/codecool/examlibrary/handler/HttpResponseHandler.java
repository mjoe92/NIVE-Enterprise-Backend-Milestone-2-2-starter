package com.codecool.examlibrary.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpResponseHandler {

    public ResponseEntity<?> getEntity(Object object, HttpStatus status) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(new HttpHeaders())
                .body(object);
    }
}
