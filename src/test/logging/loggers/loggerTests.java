package test.logging.loggers;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ChargingStationLoggerTest.class, EnergySourceLoggerTest.class, SystemLoggerTest.class })
public class loggerTests {

}
