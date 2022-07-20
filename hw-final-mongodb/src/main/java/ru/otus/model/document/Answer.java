package ru.otus.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.common.QuestionType;

import javax.annotation.Nonnull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer {

    protected String label;

    @Nonnull
    protected QuestionType questionType;
}
