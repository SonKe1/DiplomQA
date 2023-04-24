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

public class BuyGateTest {
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
    void buyPositiveAllFieldValidApproved() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SqlHelp.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SqlHelp.getPaymentStatus());
    }

    @Test
    void buyNegativeAllFieldEmpty() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonth00OverThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCirillic() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }
}
