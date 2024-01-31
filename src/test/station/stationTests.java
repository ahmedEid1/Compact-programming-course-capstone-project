package test.station;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ChargingStationTest.class, StationWaitingQueueTest.class })
public class stationTests {

}
