package com.automation.station;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ FuelStationTest.class, StationAddressTest.class })
public class TestSuites {
	public static String stationId;
}
