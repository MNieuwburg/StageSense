package storm.starter.bolt;

import java.util.Map;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.google.gson.stream.JsonReader;


@SuppressWarnings("deprecation")
public class NikeBolt implements IRichBolt {

	 OutputCollector _collector;
	 
		String _accessToken = "";
		String s = "";
		String w = "";
		JsonReader reader = null;
	  


		public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
	        _collector = collector;
			
		}
	 

		@Override
		public void execute(Tuple input) {
	    	//Get accesstoken from the spout
	    	_accessToken = input.getString(0);


	    	//Get Sport Data
	   		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.nike.com/me/sport?access_token=" +_accessToken);
	   		request.addHeader("Accept", "application/json");
	   		request.addHeader("appid", "fuelband");
	   		Response response = request.send();
	   		s = response.getBody();
	   		
	   		//Get Activity Data
	   		OAuthRequest request1 = new OAuthRequest(Verb.GET, "https://api.nike.com/me/sport/activities?access_token=" +_accessToken);
	   		request1.addHeader("Accept", "application/json");
	   		request1.addHeader("appid", "fuelband");
	   		Response response1 = request1.send();
	   		w = response1.getBody();
	   		
	   		//Send Sport and Activity data to next bolt
	   		_collector.emit(new Values(s, w));
	   		_collector.ack(input);
			
		}

	

	public void cleanup(){
		
	}
    
	
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	
    	declarer.declare(new Fields("SportData", "Activities"));
    	
    }


	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

 





}
