package storm.starter;

import storm.starter.bolt.NikeBolt;
import storm.starter.bolt.NikeSentBolt;
import storm.starter.spout.NikeSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.drpc.DRPCSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;

/**
 * This topology demonstrates Storm's stream groupings and multilang capabilities.
 */
@SuppressWarnings("deprecation")
public class NikeTopology {
 
	static final String REQUEST_STREAM_ID = NikeBolt.class.getName() + "/request-stream";
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
//        
//        LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("nike");
//        builder.addBolt(new NikeLoginBolt(), 5);
//        builder.addBolt(new NikeBolt(), 10);
    	
    	
        TopologyBuilder builder = new TopologyBuilder();
        LocalDRPC drpc = new LocalDRPC();
        
        DRPCSpout drpcSpout = new DRPCSpout("drpc-query", drpc);
        builder.setSpout("drpc-input", drpcSpout);
        
        
        builder.setSpout("spout", new NikeSpout());
        builder.setBolt("data", new NikeBolt())
        						.noneGrouping("spout");
        builder.setBolt("send", new NikeSentBolt())
        						.allGrouping("data");
        						
        
        Config conf = new Config();
        conf.setDebug(false);
        conf.setMaxTaskParallelism(1);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word-count", conf, builder.createTopology());

        
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println(drpc.execute("drpc-query", "test"));
        
        Thread.sleep(10000);
        
        drpc.shutdown();
        cluster.shutdown();


        
    }

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

		
}
