package test.energy;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ EnergySourceTest.class, ReservedBatteryTest.class })
public class energyTests {

}
