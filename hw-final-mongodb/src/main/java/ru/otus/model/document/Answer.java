package ru.otus.model.document;

import lombok.*;
import ru.otus.model.common.QuestionType;

import javax.annotation.Nonnull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer {

    protected String label;

    @Nonnull
    protected QuestionType questionType;
}
