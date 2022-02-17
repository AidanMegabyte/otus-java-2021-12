package ru.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.atm.model.AtmCell;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Тесты класса ячейки банкомата")
public class AtmCellTests {

    private AtmCell atmCell;

    @BeforeEach
    public void init() {
        atmCell = new AtmCell();
    }

    @Test
    @DisplayName("Проверяем, что ячейка создается пустой")
    public void testIsEmptyAfterCreation() {
        assertThat(atmCell.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Проверяем, что в ячейку можно добавить купюры")
    public void testCanPutCash() {
        atmCell.putCash(100500);
        assertThat(atmCell.getBalance()).isEqualTo(100500);
    }

    @Test
    @DisplayName("Проверяем, что в ячейку нельзя добавить отрицательное количество купюр")
    public void testCannotPutNegativeCash() {
        assertThatThrownBy(() -> atmCell.putCash(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что из ячейки можно получить купюры")
    public void testCanGetCash() {
        atmCell.putCash(100500);
        var cash = atmCell.getCash(777);
        assertThat(cash).isEqualTo(777);
        assertThat(atmCell.getBalance()).isEqualTo(99723);
    }

    @Test
    @DisplayName("Проверяем, что из ячейки нельзя получить отрицательное количество купюр")
    public void testCannotGetNegativeCash() {
        assertThatThrownBy(() -> atmCell.getCash(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что из ячейки нельзя получить купюр больше, чем в ней есть")
    public void testCannotGetCashMoreThanExists() {
        atmCell.putCash(777);
        var cash = atmCell.getCash(100500);
        assertThat(cash).isEqualTo(777);
        assertThat(atmCell.getBalance()).isEqualTo(0);
    }
}
