package com.automation.station;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.automation.station.bean.Alt_fuel_station;
import com.automation.station.bean.StreetAddress;
import com.automation.util.RestApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ankit Shah
 *
 *         Use the Station ID from previous test to query the API and return the
 *         Street Address of that station. Verify the Station Address is 208
 *         Barton Springs, Austin, Texas, USA, 78704
 */
public class StationAddressTest {

	private final static String VERIFY_ADDRESS = "208 Barton Springs Rd, Austin, TX, USA, 78704";
	private Logger log = Logger.getLogger(StationAddressTest.class.getName());

	@Test
	public void stationAddressTest() {

		String key = "m8ik2ZLzoI2DODHVjkMws8EiSgfhu2wLZkPk0pdw";
		String url = "https://api.data.gov/nrel/alt-fuel-stations/v1/";
		url += TestSuites.stationId + ".json?api_key=" + key;
		log.debug("Making reqest on '" + url + "' for getting stations detail for id " + TestSuites.stationId);
		try {
			// Make request for fuel station by station id
			RestApi restApi = new RestApi();
			String json = restApi.request(url);
			log.debug("Response : " + json);
			Assert.assertNotNull("Failed to get response", json);
			// Convert JSON response to JAVA bean
			log.debug("Converting json to java bean");
			ObjectMapper mapper = new ObjectMapper();
			StreetAddress fs = null;
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			fs = mapper.readValue(json, StreetAddress.class);
			Assert.assertNotNull("Failed to convert json to java bean", fs);
			log.debug("Converted json to java bean");
			log.debug("Verifying address");
			Alt_fuel_station afs = fs.getAlt_fuel_station();
			String address = afs.getStreet_address() + ", " + afs.getCity() + ", " + afs.getState() + ", USA, "
					+ afs.getZip();
			log.debug("Actual address : " + address);
			log.debug("Expected address : " + VERIFY_ADDRESS);

			// Verify for valid address
			Assert.assertTrue("In valid Street address", VERIFY_ADDRESS.equalsIgnoreCase(address));
			log.debug("Address verified");

		} catch (Exception e) {
			log.error("Failed get station info for id "+TestSuites.stationId+ "\n" + e);
		}
	}
}
