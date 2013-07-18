package storm.starter.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import nl.sense_os.api.Api;
import nl.sense_os.api.Connector;
import nl.sense_os.objects.Device;
import nl.sense_os.objects.Group;
import nl.sense_os.objects.Params;
import nl.sense_os.objects.Sensor;
import nl.sense_os.objects.SensorData;

import java.util.Scanner;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class OpenWebSpout extends BaseRichSpout {
	//private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/{0}/activities/date/{1}.json";
	  
	
    SpoutOutputCollector _collector;
    Random _rand;    
    
    String credentials = "credentials.txt";
    String b = "";
    
	public OpenWebSpout(){
//		/*Api.login(credentials);
//		Params.General.verbosity = true;*/
//		String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/{0}/activities/date/{1}.json";
//		 OAuthService service = new ServiceBuilder()
//         .provider(FitbitApi.class)
//         .apiKey("0b4ed4c3fe2c4e2b81f8496be6ad9c5e")
//         .apiSecret("d3ac6fb2a05e447381567083f172d983")
//         .build();
//		 	Scanner in = new Scanner(System.in);
//		    
//		 	System.out.println("=== Twitter's OAuth Workflow ===");
//		    System.out.println();
//
//		    // Obtain the Request Token
//		    System.out.println("Fetching the Request Token...");
//		    Token requestToken = service.getRequestToken();
//		    System.out.println("Got the Request Token!");
//		    System.out.println(requestToken);
//		    System.out.println();

//		    System.out.println("Now go and authorize Scribe here:");
//		    System.out.println(service.getAuthorizationUrl(requestToken));
//		    System.out.println("And paste the verifier here");
//		    System.out.print(">>");
//		    Verifier verifier = new Verifier(in.nextLine());
//		    System.out.println();

//		    // Trade the Request Token and Verfier for the Access Token
//		    System.out.println("Trading the Request Token for an Access Token...");
//		    Token accessToken = service.getAccessToken(requestToken, verifier);
//		    System.out.println("Got the Access Token!");
//		    System.out.println("(if your curious it looks like this: " + accessToken + " )");
//		    System.out.println(accessToken);
//		    System.out.println();
		    
//		    // Now let's go and ask for a protected resource!
//		    System.out.println("Now we're going to access a protected resource...");
//		 	OAuthRequest request = new OAuthRequest(Verb.POST, PROTECTED_RESOURCE_URL);
//		 	request.addBodyParameter("status", "this is sparta! *");
//		    service.signRequest(accessToken, request);
//		    Response response = request.send();
//		    System.out.println("Got it! Lets see what we found...");
//		    System.out.println();
//		    String s = response.getBody();
//		    int r = response.getCode();
//		    System.out.println(s);
//		    System.out.println(r);
//		    System.out.println();
//		    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
//	        

		    
	}
    
	
	
    public void nextTuple() {
    	Utils.sleep(1000);
    	_collector.emit(new Values(b));
    }	

    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
    	_collector = collector;
	}


}
