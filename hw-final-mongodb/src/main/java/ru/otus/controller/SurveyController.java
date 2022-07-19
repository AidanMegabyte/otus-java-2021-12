package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.dto.SurveyDto;
import ru.otus.model.dto.SurveyRequest;
import ru.otus.service.SurveyService;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/list")
    public List<SurveyDto> getList() {
        return surveyService.getList();
    }

    @GetMapping("/{id}")
    public SurveyDto get(@PathVariable("id") long id) {
        return surveyService.get(id);
    }

    @PostMapping("/create")
    public SurveyDto create(@RequestBody SurveyRequest surveyRequest) {
        return surveyService.save(null, surveyRequest);
    }

    @PutMapping("/{id}")
    public SurveyDto modify(@PathVariable("id") long id, @RequestBody SurveyRequest surveyRequest) {
        return surveyService.save(id, surveyRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        surveyService.delete(id);
    }
}
