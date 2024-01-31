package test.cars;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CarTest.class, ChargingCarTest.class })
public class carTests {

}
