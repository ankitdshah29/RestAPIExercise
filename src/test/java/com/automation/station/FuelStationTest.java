package com.automation.station;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.automation.station.bean.FuelStation;
import com.automation.station.bean.Fuel_stations;
import com.automation.util.RestApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ankit Shah
 *
 *         Query for nearest stations to Austin, TX that are part of the
 *         “ChargePoint Network”. Verify that “HYATT AUSTIN” appears in the
 *         results. Store/save the Station Id of the HYATT AUSTIN station.
 * 
 */
public class FuelStationTest {

	private Logger log = Logger.getLogger(FuelStationTest.class.getName());

	@Test
	public void fuilStationTest() {
		BasicConfigurator.configure();
		String key = "m8ik2ZLzoI2DODHVjkMws8EiSgfhu2wLZkPk0pdw";
		String url = "https://api.data.gov/nrel/alt-fuel-stations/v1/nearest.json?";
		url += "api_key=" + key + "&location=Austin+TX&ev_network=ChargePoint%20Network";
		log.debug("Making reqest on '" + url + "' for getting nearest fuel stations");
		try {
			// Make request for nearest fuel station
			RestApi restApi = new RestApi();
			String json = restApi.request(url);
			log.debug("Response : " + json);
			Assert.assertNotNull("Failed to get response", json);
			// convert JSON response to Java bean
			log.debug("Converting json to java bean");
			ObjectMapper mapper = new ObjectMapper();
			FuelStation fs = null;
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			fs = mapper.readValue(json, FuelStation.class);
			Assert.assertNotNull("Failed to convert json to java bean", fs);
			log.debug("Converted json to java bean");
			/*
			 * Verify "HYATT AUSTIN" is appears and store its station id for
			 * next test
			 */
			log.debug("Verifying 'HYATT AUSTIN' is appear");
			Assert.assertTrue("HYATT AUSTIN is not appears in the result", isHyattAustinStationPresent(fs));
			log.debug("Verified 'HYATT AUSTIN' is appear");
			log.debug("id : " + TestSuites.stationId);
		} catch (Exception e) {
			log.error("Failed gett nearest fuel stations " + e);
		}
	}

	/**
	 * Verify is Hyatt Austin station is present
	 * 
	 * @param fs
	 *            - FuelStation
	 * @return True / False
	 */
	private boolean isHyattAustinStationPresent(FuelStation fs) {
		Fuel_stations fuel_stations[] = fs.getFuel_stations();
		for (int i = 0; i < fuel_stations.length; i++) {
			Fuel_stations fuel_station = fuel_stations[i];
			if ("HYATT AUSTIN".equalsIgnoreCase(fuel_station.getStation_name())) {
				TestSuites.stationId = fuel_station.getId();
				return true;
			}
		}
		return false;
	}
}
