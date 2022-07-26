package ru.otus.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "survey")
public class SurveyTemplate {

    @Id
    @Nonnull
    private Long id;

    @Nonnull
    private String title;

    private String subtitle;

    @Nonnull
    private List<Question> questions;
}
