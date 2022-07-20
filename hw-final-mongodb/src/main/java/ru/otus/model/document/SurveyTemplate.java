package ru.otus.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "survey")
public class SurveyTemplate {

    @Id
    private Long id;

    @Nonnull
    private String title;

    private String subtitle;

    @Nonnull
    private List<Question> questions;
}
