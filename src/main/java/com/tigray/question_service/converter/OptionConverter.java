package com.tigray.question_service.converter;


import com.tigray.question_service.Model.Option;
import com.tigray.question_service.dto.OptionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OptionConverter {

    public List<Option> convert(List<OptionDto> optionDto1) {
        if (optionDto1 == null) {
            return new ArrayList<>();  // Return an empty list if the input is null
        }
        return optionDto1.stream()
                .map(optionDto -> Option.builder()
                        .optionLabel(optionDto.optionLabel())
                        .optionText(optionDto.optionText())
                        .build())
                .collect(Collectors.toList());
    }
}
