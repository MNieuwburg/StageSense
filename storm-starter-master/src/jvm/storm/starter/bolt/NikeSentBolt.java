package storm.starter.bolt;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import nl.sense_os.api.Api;
import nl.sense_os.objects.Params;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import nl.sense_os.api.Api;
import nl.sense_os.objects.Device;
import nl.sense_os.objects.Group;
import nl.sense_os.objects.Params;
import nl.sense_os.objects.Sensor;
import nl.sense_os.objects.SensorData;



public class NikeSentBolt implements IRichBolt {

	 OutputCollector _collector;
	 
		String _accessToken = "";
		String Activities = "";
		String SportData = "";
		long Time = System.currentTimeMillis() / 1000L;
		
	  

	  public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
	        _collector = collector;
	        
	    }
	 
	  @Override
		public void execute(Tuple input) {
			StringWriter out = new StringWriter();
			StringWriter out2 = new StringWriter();
			//Get the Sport and Activity Data
			SportData = input.getString(0);
	    	Activities = input.getString(1);
	    	    	
	    	
	    	postRunData();
	    	postDistanceRecord();
	    	postLongestRecord();
	    	postDurationData();
	    	    	
//	    	System.out.println("Sport Data: " +SportData);
//	    	System.out.println("Activities: " +Activities);
	    	
		}
	 

	 
	    public void finish(){
		   
	    }
	

	public void cleanup(){
		
	}
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	
    	declarer.declare(new Fields("id", "word"));
    	
    }


    	private static void postRunData() {
		int sensorId = 401197;
		
		//Creating Dummy-Data for activities          >>>REWORK<<<
    	JsonObject act = new JsonObject();
    	act.addProperty("activityType","RUN");
    	act.addProperty("duration", "5:45:00.000");
    	act.addProperty("startTime", "2013-07-10T12:31:24Z");
    	String activities = act.toString();
    	
    	JsonObject value2 = new JsonObject();
    	value2.addProperty("date", System.currentTimeMillis() / 1000L);
    	value2.addProperty("value", activities);


    	JsonArray runArray = new JsonArray();
    	runArray.add(value2);

    	JsonObject run = new JsonObject();
    	run.add("data", runArray);

		boolean posted = Api.postSensorData(sensorId, run);
		System.out.println("Test postSensorData()... " + (posted ? "Success!" : "Fail!"));
	}
	
	private static void postDistanceRecord(){
		int sensorId = 401408;
		
		//Dummy data Distance record
		
		JsonObject lifetimeDistance = new JsonObject();
		lifetimeDistance.addProperty("recordType", "LIFETIMEDISTANCE");
		lifetimeDistance.addProperty("recordValue", "49.13999938964844");
		String distance = lifetimeDistance.toString();
		
		JsonObject distanceValue = new JsonObject();
		distanceValue.addProperty("date", System.currentTimeMillis() / 1000L);
		distanceValue.addProperty("value", distance);
    	
    	JsonArray distanceArray = new JsonArray();
    	distanceArray.add(distanceValue);

    	JsonObject distanceRecord = new JsonObject();
    	distanceRecord.add("data", distanceArray);
    	
		boolean posted = Api.postSensorData(sensorId, distanceRecord);
		System.out.println("Test postSensorData()... " + (posted ? "Success!" : "Fail!"));
		
	}

	private static void postLongestRecord(){
		int sensorId = 401410;
	
		//Dummy data distance record
		
		JsonObject longestDistance = new JsonObject();
		longestDistance.addProperty("recordType", "LONGESTRUNDISTANCE");
		longestDistance.addProperty("recordValue", "24.139999389648438");
		String longest = longestDistance.toString();
		
		JsonObject longestValue = new JsonObject();
		longestValue.addProperty("date", System.currentTimeMillis() / 1000L);
		longestValue.addProperty("value", longest);
		
		JsonArray longestArray = new JsonArray();
    	longestArray.add(longestValue);

    	JsonObject longestRecord = new JsonObject();
    	longestRecord.add("data", longestArray);
    	
		boolean posted = Api.postSensorData(sensorId, longestRecord);
		System.out.println("Test postSensorData()... " + (posted ? "Success!" : "Fail!"));
		
	}
	
	private static void postDurationData(){
		int sensorId = 401411;
		
		//Dummy data duration record
		
		JsonObject lifetimeDuration = new JsonObject();
		lifetimeDuration.addProperty("recordType", "LIFETIMEDURATION");
		lifetimeDuration.addProperty("recordValue", "8:04:11.000");
		String duration = lifetimeDuration.toString();
		
		JsonObject durationValue = new JsonObject();
		durationValue.addProperty("date", System.currentTimeMillis() / 1000L);
		durationValue.addProperty("value", duration);
		
		JsonArray durationArray = new JsonArray();
		durationArray.add(durationValue);

    	JsonObject durationRecord = new JsonObject();
    	durationRecord.add("data", durationArray);
		
		boolean posted = Api.postSensorData(sensorId, durationRecord);
		System.out.println("Test postSensorData()... " + (posted ? "Success!" : "Fail!"));
	}


	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
