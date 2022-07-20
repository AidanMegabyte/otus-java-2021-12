package ru.otus.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.model.common.QuestionType;

import javax.annotation.Nonnull;
import java.util.List;

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
    private List<? extends Answer> answers;
}
