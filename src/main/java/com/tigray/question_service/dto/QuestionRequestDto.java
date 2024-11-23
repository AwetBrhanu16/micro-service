package com.tigray.question_service.dto;

import java.util.List;

public record QuestionRequestDto(QuestionDto questionDto,
                                 List<OptionDto>  optionDto) {
}
