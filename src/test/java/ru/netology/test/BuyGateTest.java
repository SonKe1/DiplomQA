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
    void buyNegativeNumberCard15Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getFifteenNumberCardNumber());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getFakerNumberCardNumber());
        payment.waitNotificationFailure();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getOneNumberMonth());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getThirteenMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getZeroMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeMonth00OverThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getPreviousMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getEmptyYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelp.getOrderCount());
    }


    @Test
    void buyNegativeYearUnderThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getPreviousYearInField());
        payment.waitNotificationExpiredError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getPlusSixYearInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getOneNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getOTwoNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getOnlySurnameInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCirillic() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getRusName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNumberInName() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getNumberInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelp.getSpecialSymbolInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SqlHelp.getOrderCount());
    }
}
