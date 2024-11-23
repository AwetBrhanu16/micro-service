package com.tigray.question_service.Service;

import com.tigray.question_service.DAO.QuestionDAO;
import com.tigray.question_service.Model.Option;
import com.tigray.question_service.Model.Question;
import com.tigray.question_service.Model.QuestionsForStudent;
import com.tigray.question_service.Model.Response;
import com.tigray.question_service.converter.OptionConverter;
import com.tigray.question_service.dto.OptionDto;
import com.tigray.question_service.dto.QuestionDto;
import com.tigray.question_service.dto.QuestionForQuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final OptionConverter converter;
    private final QuestionDAO questionDAO;


    public List<Question> getAllQuestions() {
       return questionDAO.findAll();
    }


    public List<Question> getQuestionByCategory(String category) {
       return questionDAO.findByCategory(category);
    }


    public String addQuestion(QuestionDto questionDto, List<OptionDto> optionDto) {

        List<Option> options = converter.convert(optionDto);
        Question questions = Question
                .builder()
                .option(options)
                .category(questionDto.category())
                .title(questionDto.title())
                .difficultyLevel(questionDto.difficultyLevel())
                .answer(questionDto.answer())
                .questionText(questionDto.questionText())
                .build();

        questionDAO.save(questions);
        return "question added successfully";
    }


    public void deleteQuestion(Integer id) {
        boolean exist = questionDAO.existsById(id);
        if (!exist) {
            throw new IllegalStateException("question with id "+id+" does not exist");
        }
            questionDAO.deleteById(id);
    }


    public ResponseEntity<List<Integer>> getQuestionForQuiz(QuestionForQuizDto questionForQuizDto) {
        List<Integer> questions = questionDAO.findRandomQuestionsByCategory(questionForQuizDto.category(),questionForQuizDto.numQuestion());
        System.out.println(questions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    public ResponseEntity<List<QuestionsForStudent>> getQuestionFromId(List<Integer> ids) {
        List<QuestionsForStudent> questionsForStudent = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer id : ids){
            questions.add(questionDAO.findById(id).get());
        }
        for (Question question : questions){
            QuestionsForStudent questionsWrapper = new QuestionsForStudent();
            questionsWrapper.setId(question.getId());
            questionsWrapper.setQuestionTitle(question.getTitle());
            questionsWrapper.setOptions(question.getOption());
        }
        return new ResponseEntity<>(questionsForStudent,HttpStatus.OK);
    }


    public ResponseEntity<Integer> getScore(List<Response> response) {
        int right = 0;
        for (Response resp : response) {
            Question question = questionDAO.findById(resp.getId()).get();
            if (resp.getResponse().equals(question.getAnswer()))
                right++;

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }


}