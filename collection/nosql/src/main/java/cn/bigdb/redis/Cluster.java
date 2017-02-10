package cn.bigdb.redis;

import java.io.IOException;
import java.util.HashSet;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class Cluster {

	   public static void main (String args[]) {
	        HashSet<HostAndPort> nodes = new HashSet<>();
	        nodes.add(new HostAndPort("192.168.0.102", 7001));
	        nodes.add(new HostAndPort("192.168.0.102", 7002));
	        nodes.add(new HostAndPort("192.168.0.102", 7003));
	        nodes.add(new HostAndPort("192.168.0.102", 7004));
	        nodes.add(new HostAndPort("192.168.0.102", 7005));
	        nodes.add(new HostAndPort("192.168.0.102", 7000));
	        
	        JedisCluster cluster = new JedisCluster(nodes);
	        
	        cluster.set("key1", "1000");
	        String string = cluster.get("key1");
	        System.out.println(string);
	        
	        try {
				cluster.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	
}
