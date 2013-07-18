package storm.starter.spout;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;

import nl.sense_os.api.Api;
import nl.sense_os.objects.Params;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.google.gson.stream.JsonReader;


public class NikeSpout extends BaseRichSpout implements Serializable{
	//private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/{0}/activities/date/{1}.json";
	  
	
    SpoutOutputCollector _collector;
    Random _rand;    
    
    String email = "";
    String password = "";
    
    String credentials = "credentials.txt";	
    String b = "";

	String CLIENT_ID = "";
	String CLIENT_SECRET = "";
	String _accessToken = "";

	public NikeSpout(){

		//Read CLIENT_ID from file
		try {
			CLIENT_ID = readFile("ClientID.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Read CLIENT_SECRET from file
		try {
			CLIENT_SECRET = readFile("ClientSECRET.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try
	    {
			//Get users from database
	      // create our mysql database connection
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost/NikePlusDB";
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, "root", "password");
	      
	      //SQL Query
	      String query = "SELECT * FROM credentials";

	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(query);
	      
	      // iterate through the java resultset
	      while (rs.next())
	      {
	        email = rs.getString("email");
	        password = rs.getString("password");
	      }
	      st.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      e.printStackTrace();
	    }

		
		//Getting Accesstoken from Nike
		OAuthRequest request0 = new OAuthRequest(Verb.POST, "https://api.nike.com/nsl/v2.0/user/login?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&app=fuelband");
   		request0.addHeader("Accept", "application/json");
   		request0.addHeader("appid", "fuelband");
   		request0.addBodyParameter("email", email);
   		request0.addBodyParameter("password", password);
   		Response response0 = request0.send();
   		String z = response0.getBody();
   		System.out.println(" ---- LOGIN ----");
//   		System.out.println("Gebruikte email: " +email);
//   		System.out.println("Gebruikte password: " +password);
//   		System.out.println(z);
   		System.out.println();
   		
   		
   		JsonReader reader = null;	
   		
   		
   		//Getting only the accesToken out of the response body
   		try {
   					InputStream is = new ByteArrayInputStream(z.getBytes());
   						reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
   						reader.setLenient(true);
   						reader.beginObject();
   						while (reader.hasNext()) {
   							String name = reader.nextName();
   							if (name.equals("access_token")) _accessToken = reader.nextString();
   							else reader.skipValue();
   						}
   						reader.endObject();
   					}
   					catch (IllegalStateException ise ) {}
   					catch (IOException e) {
						e.printStackTrace();
					}
   					System.out.println("Yes! We've found it!");
					System.out.println("Here is your AccessToken enjoy: " + _accessToken);
					
					
					//login CommonSense
	    			Api.login(credentials);

	    			// verbosity
	    			Params.General.verbosity = true;
	    			

				}
	
	
    public void nextTuple() {
    	Utils.sleep(1000);
    	_collector.emit(new Values(_accessToken));
    }	
   
    private String readFile( String file ) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );																																																																																																																																																																																																																																																											
        }

        return stringBuilder.toString();
    }


    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("AccessToken"));
    }

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
    	_collector = collector;
	}


}
