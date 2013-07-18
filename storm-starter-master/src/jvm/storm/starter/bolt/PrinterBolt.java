package storm.starter.bolt;

import java.util.Map;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FitbitApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import backtype.storm.task.IOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;


public class PrinterBolt extends BaseBasicBolt {

	 OutputCollector _collector;
	 
	  

	  public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
	        _collector = collector;
	    }
	 

	  
	    public void execute(Tuple tuple, BasicOutputCollector collector) {
	   	 OAuthService service = new ServiceBuilder()
	     .provider(FitbitApi.class)
	     .apiKey("0b4ed4c3fe2c4e2b81f8496be6ad9c5e")
	     .apiSecret("d3ac6fb2a05e447381567083f172d983")
	     .build();
	   	 
		String PROTECTED_RESOURCE_URL = "http://api.fitbit.com/1/user/-/profile.xml";
		String STRING2	= "http://api.fitbit.com/1/user/-/activities/log/favorite/90009.xml";
	    String STRING1 = "http://api.fitbit.com/1/user/-/activities/favorite.xml";
	    String STRING3 = "http://api.fitbit.com/1/user/-/activities/log/favorite/90009.xml";
	   	
	    Token accessToken = new Token ("aea11fec93e19badab3e5fb596ed95e5", "03d9649c0d816590b095054449add700");
	   	System.out.println(accessToken);

	   	 
		    // Now let's go and ask for a protected resource!
		    //System.out.println("Now we're going to access a protected resource...");
		    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		    request.getBodyContents();
		    //request.addBodyParameter("status", "this is sparta! *");
		    service.signRequest(accessToken, request);
		    Response response = request.send();
		  //  System.out.println("Got it! Lets see what we found...");
		    System.out.println();
		    String s = response.getBody();
//		    int r = response.getCode();
		    System.out.println(" ---- GET PROFILE ----");
		    System.out.println(s);
//		    System.out.println(r);
		    System.out.println();
//		    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
		    
		    OAuthRequest request2 = new OAuthRequest(Verb.POST, STRING2);
		    request2.addBodyParameter("12", "90009");
		    service.signRequest(accessToken, request2);
		    Response response2 = request2.send();
		    String q = response2.getMessage();
		    System.out.println(" ---- POST ACTIVITIES ----");
		    System.out.println(q);
		    System.out.println();
		    
		    OAuthRequest request3 = new OAuthRequest(Verb.GET, STRING1);
		    request3.getBodyParams();
		    service.signRequest(accessToken, request3);
		    Response response3 = request3.send();
		    String w = response3.getBody();
		    System.out.println(" ---- GET ACTIVITIES ----");
		    System.out.println(w);
		    System.out.println();
		    
		    OAuthRequest request4 = new OAuthRequest(Verb.DELETE, STRING3);
		    service.signRequest(accessToken, request4);
		    Response response4 = request4.send();
		    Boolean e = response4.isSuccessful();
		    System.out.println(" ---- DELETE ACTIVITIES ----");
		    System.out.println("There was information, and it has been deleted: "+ e);
		    System.out.println();
	    	}
	    

	

	public void cleanup(){
		
	}
    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	
    	declarer.declare(new Fields("word"));
    	
    }

}
