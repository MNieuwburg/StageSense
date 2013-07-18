import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import nl.sense_os.api.Api;
import nl.sense_os.objects.Device;
import nl.sense_os.objects.Group;
import nl.sense_os.objects.Params;
import nl.sense_os.objects.Sensor;
import nl.sense_os.objects.SensorData;

public class CreateSensor {

	public static void main(String args[]) {
		// login, credential file should contain a JsonObject with 'username' and 'password'.
		String credentials = "credentials.txt";
		Api.login(credentials);

		// verbosity
		Params.General.verbosity = true;

		// start API test
		System.out.println("Starting API TEST... \n");
	
		createSensor();
		
	
	}
	
	private static void createSensor() {
		// settings for creating new sensor
		String name = "Sensor";
		String device_type = "smartphone";
		String data_type = "json";

		JsonObject json = new JsonObject();
		json.addProperty("data", "JSONArray");
		String data_structure = json.toString();

		Sensor s = new Sensor(name, device_type, name, data_type, data_structure);

		boolean created = Api.createSensor(s, true);
		System.out.println("Test createSensor()... " + (created ? "Success!" : "Fail!"));
	}
	
	
	
}
