package com.es.demo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EsClient {
	
	/** 配置文件参数 */
	private static Properties properties = null;
	
	private static String es_ip = null; //ip
	private static int es_port = 0; //端口
	private static String es_cluster_name = null; //集群名字
	
	private static TransportClient client = null;
	
	public TransportClient getClient() throws IOException{
		properties = new Properties();
		properties.load(TransportClient.class.getClassLoader().getResourceAsStream("env.properties"));
		
		es_ip = properties.getProperty("target.es.ip");
		es_port = Integer.valueOf(properties.getProperty("target.es.port"));
		es_cluster_name = properties.getProperty("target.es.cluster.name");
		System.out.println("原始地址，es_ip = "+es_ip);
		String [] hosts = es_ip.split(",");
		System.out.println("切割之后的esIp数组  = "+hosts);//针对于以后的集群部署我这里只有 一台服务器
		
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name",es_cluster_name)
		        .put("client.transport.sniff", true).build();
		client = TransportClient.builder().settings(settings)
				.build();
		for(int i=0;i<hosts.length;i++){
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hosts[i]), es_port));
		}
		return client;
	} 

}
