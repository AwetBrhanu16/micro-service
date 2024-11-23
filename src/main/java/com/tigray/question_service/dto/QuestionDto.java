package com.tigray.question_service.dto;

import java.util.List;

public record QuestionDto(List<OptionDto> optionDto,
                          String answer,
                          String title,
                          String questionText,
                          String category,
                          String difficultyLevel){
}
