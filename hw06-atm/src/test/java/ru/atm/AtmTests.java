package ru.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.atm.exception.AtmException;
import ru.atm.model.Atm;
import ru.atm.model.AtmCell;
import ru.atm.model.BanknoteDenomination;
import ru.atm.model.HasSameDenominationBanknotes;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Тесты класса банкомата")
public class AtmTests {

    private Atm atm;

    @BeforeEach
    public void init() {
        var atmCells = new HashMap<BanknoteDenomination, HasSameDenominationBanknotes>();
        atmCells.put(BanknoteDenomination.ONE_HUNDRED, new AtmCell());
        atmCells.put(BanknoteDenomination.TWO_HUNDREDS, new AtmCell());
        atmCells.put(BanknoteDenomination.FIVE_HUNDREDS, new AtmCell());
        atmCells.put(BanknoteDenomination.ONE_THOUSAND, new AtmCell());
        atmCells.put(BanknoteDenomination.TWO_THOUSANDS, new AtmCell());
        atmCells.put(BanknoteDenomination.FIVE_THOUSANDS, new AtmCell());
        atm = new Atm(atmCells);
    }

    @Test
    @DisplayName("Проверяем, что банкомат нельзя создать без ячеек")
    public void testCannotCreateWithoutCells() {
        assertThatThrownBy(() -> new Atm(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Atm(new HashMap<>())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат нельзя добавить ячейку для номинала купюры = null")
    public void testCannotAddCellForNullDenomination() {
        var atmCells = new HashMap<BanknoteDenomination, HasSameDenominationBanknotes>();
        atmCells.put(null, new AtmCell());
        assertThatThrownBy(() -> new Atm(atmCells)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат нельзя добавить null в качестве ячейки")
    public void testCannotAddNullCell() {
        var atmCells = new HashMap<BanknoteDenomination, HasSameDenominationBanknotes>();
        atmCells.put(BanknoteDenomination.ONE_HUNDRED, null);
        assertThatThrownBy(() -> new Atm(atmCells)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что банкомат создается пустым")
    public void testIsEmptyAfterCreation() {
        assertThat(atm.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат можно внести наличные")
    public void testCanPutCash() {
        var cash = new HashMap<Integer, Integer>();
        cash.put(100, 10);
        cash.put(200, 7);
        cash.put(1000, 4);
        cash.put(5000, 2);
        assertThat(atm.putCash(cash).isEmpty()).isTrue();
        assertThat(atm.getBalance()).isEqualTo(16400);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат можно внести наличные только купюрами допустимых номиналов")
    public void testCanPutCashOnlyAllowedDenominations() {
        var cash = new HashMap<Integer, Integer>();
        cash.put(100, 10);
        cash.put(777, 4);
        var returnedCash = atm.putCash(cash);
        assertThat(returnedCash.size()).isEqualTo(1);
        assertThat(returnedCash.get(777)).isEqualTo(4);
        assertThat(atm.getBalance()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат нельзя внести 0 купюр")
    public void testCannotPutCashEmpty() {
        assertThatThrownBy(() -> atm.putCash(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> atm.putCash(new HashMap<>())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат нельзя внести купюры с номиналом null")
    public void testCannotPutCashNullDenomination() {
        var cash = new HashMap<Integer, Integer>();
        cash.put(100, 10);
        cash.put(null, 7);
        assertThatThrownBy(() -> atm.putCash(cash)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат нельзя внести купюры любого номинала в количестве null")
    public void testCannotPutCashNullQty() {
        var cash = new HashMap<Integer, Integer>();
        cash.put(100, 10);
        cash.put(200, null);
        assertThatThrownBy(() -> atm.putCash(cash)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем, что в банкомат нельзя внести купюры любого номинала в количестве < 1")
    public void testCannotPutCashNegativeQty() {
        var cash = new HashMap<Integer, Integer>();
        cash.put(100, 10);
        cash.put(200, 0);
        assertThatThrownBy(() -> atm.putCash(cash)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверяем выдачу наличных минимальным количеством купюр")
    public void testGetCashMinimumBanknotes() {

        var cashPut = new HashMap<Integer, Integer>();
        cashPut.put(100, 10);
        cashPut.put(200, 10);
        cashPut.put(500, 10);
        cashPut.put(1000, 10);
        cashPut.put(2000, 10);
        cashPut.put(5000, 10);
        atm.putCash(cashPut);

        var cashGet = atm.getCash(9900);
        assertThat(cashGet.size()).isEqualTo(4);
        assertThat(cashGet.get(200)).isEqualTo(2);
        assertThat(cashGet.get(500)).isEqualTo(1);
        assertThat(cashGet.get(2000)).isEqualTo(2);
        assertThat(cashGet.get(5000)).isEqualTo(1);
        assertThat(atm.getBalance()).isEqualTo(78100);
    }

    @Test
    @DisplayName("Проверяем, что из банкомата нельзя получить денег больше, чем в нем есть")
    public void testCannotGetCashMoreThanExists() {
        assertThatThrownBy(() -> atm.getCash(1)).isInstanceOf(AtmException.class);
        var cash = new HashMap<Integer, Integer>();
        cash.put(100, 10);
        atm.putCash(cash);
        assertThatThrownBy(() -> atm.getCash(100500)).isInstanceOf(AtmException.class);
        assertThat(atm.getBalance()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Проверяем, что банкомат не выдает нужную сумму в отсутствии подходящих купюр")
    public void testCannotGetCashWithoutRequiredBanknotes() {
        var cashPut = new HashMap<Integer, Integer>();
        cashPut.put(200, 10);
        atm.putCash(cashPut);
        assertThatThrownBy(() -> atm.getCash(300)).isInstanceOf(AtmException.class);
        assertThat(atm.getBalance()).isEqualTo(2000);
    }
}
