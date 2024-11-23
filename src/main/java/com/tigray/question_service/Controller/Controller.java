package com.tigray.question_service.Controller;

import com.tigray.question_service.Model.Question;
import com.tigray.question_service.Model.QuestionsForStudent;
import com.tigray.question_service.Model.Response;
import com.tigray.question_service.Service.QuestionService;
import com.tigray.question_service.dto.OptionDto;
import com.tigray.question_service.dto.QuestionDto;
import com.tigray.question_service.dto.QuestionForQuizDto;
import com.tigray.question_service.dto.QuestionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

  private final QuestionService questionService;

    @GetMapping("/getAll")
    public List<Question> getAllQuestions(){
         return questionService.getAllQuestions();
    }


    @GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory (@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }


    @PostMapping("/addQuestions")
    public String addQuestion(@RequestBody QuestionRequestDto requestDto){
        QuestionDto questionDto = requestDto.questionDto();
        List<OptionDto> optionDto = requestDto.optionDto();
        return questionService.addQuestion(questionDto, optionDto);
    }


    @DeleteMapping(path = "/deleteQuestion/{id}")
    public void deleteQuestion(@PathVariable("id") Integer id){
        questionService.deleteQuestion(id);
    }


    @PostMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestBody QuestionForQuizDto questionForQuizDto){
        return questionService.getQuestionForQuiz(questionForQuizDto);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionsForStudent>> getQuestionForStudent(@RequestBody List<Integer> ids){
        return questionService.getQuestionFromId(ids);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> score(@RequestBody List<Response> response){
        return questionService.getScore(response);
    }

}
