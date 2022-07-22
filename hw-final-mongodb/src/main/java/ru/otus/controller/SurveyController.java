package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.document.SurveyTemplate;
import ru.otus.model.dto.SurveyFullDto;
import ru.otus.model.dto.SurveyRequest;
import ru.otus.model.entity.Survey;
import ru.otus.service.SurveyService;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/list")
    public List<Survey> getList() {
        return surveyService.getList();
    }

    @GetMapping("/{id}/full")
    public SurveyFullDto getFull(@PathVariable("id") long id) {
        return surveyService.getFull(id);
    }

    @GetMapping("/{id}/template")
    public SurveyTemplate getTemplate(@PathVariable("id") long id) {
        return surveyService.getTemplate(id);
    }

    @PostMapping("/create")
    public SurveyFullDto create(@RequestBody SurveyRequest surveyRequest) {
        return surveyService.save(surveyRequest);
    }

    @PutMapping("/edit")
    public SurveyFullDto modify(@RequestBody SurveyRequest surveyRequest) {
        return surveyService.save(surveyRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        surveyService.delete(id);
    }
}
