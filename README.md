RestAPIExercise
===============

RestAPIExercise is a maven project which performs below Rest API operation on "https://api.data.gov/docs/nrel/transportation/alt-fuel-stations-v1"
1. Query for nearest stations to Austin, TX that are part of the “ChargePoint Network”. Verify that “HYATT AUSTIN” appears in the results. Store/save the Station Id of the HYATT AUSTIN station.
2. Use the Station ID from previous test to query the API and return the Street Address of that station. Verify the Station Address is 208 Barton Springs, Austin, Texas, USA, 78704


Installation Instruction 
1. Clone RestAPIExercise project
2. Go to RestAPIExercise folder 
3. Run "mvn clean"
4. Run "mvn -Dtest=TestSuites test"
