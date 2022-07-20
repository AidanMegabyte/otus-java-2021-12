package ru.otus.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nonnull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RangeAnswer extends Answer {

    private int rangeStart;

    private int rangeEnd;

    @Nonnull
    private String rangeLabelStart;

    @Nonnull
    private String rangeLabelEnd;
}
