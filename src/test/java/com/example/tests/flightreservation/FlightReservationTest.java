package com.example.tests.flightreservation;

import com.example.listener.TestListener;
import com.example.pages.flightreservation.RegistrationPage;
import com.example.tests.AbstractTest;
import com.example.tests.flightreservation.model.FlightReservationTestData;
import com.example.util.Config;
import com.example.util.Constants;
import com.example.util.JsonUtil;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertTrue;

public class FlightReservationTest extends AbstractTest {
    private FlightReservationTestData testData;
    @BeforeSuite
    public void setupConfig(){
        Config.initialize();
    }
    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath){
        log.info("Test data path received: " + testDataPath);

        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);

    }
    @Test
    public void userRegistration(){
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.visit(Config.get(Constants.FLIGHT_RESERVATION_URL));
        registrationPage.enterUserDetails(testData.firstName(), testData.password());
        assertTrue(registrationPage.isAt());
    }
}
