package ru.netology.test;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelp;
import ru.netology.data.SqlHelp;
import ru.netology.page.PaymentMethod;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditGateTest {
    public static String url = System.getProperty("sut.url");

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SqlHelp.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void creditPositiveAllFieldValidApproved() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SqlHelp.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SqlHelp.getCreditRequestStatus());
    }

    @Test
    void creditNegativeAllFieldEmpty() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeYear00() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeYear1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeYearUnderThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCirillic() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeOwnerNumeric() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelp.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }
}
