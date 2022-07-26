package ru.otus.model.document;

import lombok.*;
import ru.otus.model.common.QuestionType;

import javax.annotation.Nonnull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    @Nonnull
    private QuestionType type;

    private String label;

    private boolean required;

    @Nonnull
    private List<Answer> answers;
}
