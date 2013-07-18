package storm.starter;

import storm.starter.bolt.PrinterBolt;
import storm.starter.spout.OpenWebSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;

/**
 * This topology demonstrates Storm's stream groupings and multilang capabilities.
 */
public class MyTopology {
 

    
    public static void main(String[] args) throws Exception {
        
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("word", new OpenWebSpout(), 5);
        builder.setBolt("data", new PrinterBolt(), 10)
        						.shuffleGrouping("word");
        

        Config conf = new Config();
        conf.setDebug(true);

        
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);
            
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {        
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", conf, builder.createTopology());
        
            Thread.sleep(10000);

            cluster.shutdown();
        }
        
    }

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

		
}
