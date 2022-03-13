package ru.gb.lk_loyality.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.lk_loyality.entities.Response;
import ru.gb.lk_loyality.exceptions.InvalidDateException;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleException(ResourceNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Response> handleException(InvalidDateException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        String msg = e.getMessage();
        model.addAttribute(msg);
        return "error";
    }
}
