package com.es.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.ActionWriteResponse.ShardInfo;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	EsClient esClient = new EsClient();
        TransportClient client = esClient.getClient();
        //---------------演示查询
       /* SearchRequestBuilder request = client.prepareSearch("wesley")
		        .setTypes("test").setSearchType(SearchType.QUERY_THEN_FETCH);
		//初始化查询参数
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		query.must(QueryBuilders.termQuery("author","author"));
        request.setQuery(query);
        //查询操作
        SearchResponse response = request.execute().actionGet();
        List<Test> returnList = new ArrayList<Test>();
		SearchHits hints = response.getHits();
		for(SearchHit theHit : hints){// 每条纪录
			Map<String,Object> testInfo = new HashMap<String,Object>();
			for(Map.Entry<String, Object> entity : theHit.getSource().entrySet()){
				testInfo.put(entity.getKey(), entity.getValue()==null ? null : entity.getValue().toString());// 根据数值大小，value 可能为Integer/Long 不依赖ES映射类型
			}
			returnList.add((Test)ConverterUtil.mapToObject(Test.class, testInfo));
		}
		
		for(Test test:returnList){
			System.out.println("-----------------"+test);
		}*/
		//---------------演示查询------------end
		
		//-----------演示创建索引,类型,文档-----------
        /*ObjectMapper objectMapper = new ObjectMapper();
        Test test = new Test();
        test.setAuthor("author6");
        test.setCategory("category6");
        test.setTitle("title6");
        test.setContent("content6");
        String source = objectMapper.writeValueAsString(test);
        System.out.println("--------"+source);
		IndexRequestBuilder indexBuilder = client.prepareIndex().setIndex("wesley6").setType("test6").setSource(source);
		IndexResponse indexResponse = indexBuilder.execute().actionGet();
		System.out.println("--------"+indexResponse.isCreated()+"----------");*/
		//-----------演示创建索引,类型,文档-----------end
        //------------演示删除文档-------
        /*DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete("wesley6", "test6", "AVmV5176AmysSwOEBaDt");
        DeleteResponse deleteResponse = deleteRequestBuilder.execute().actionGet();
        System.out.println("------isDelete-----"+deleteResponse.isFound());*/
        //------------演示删除文档-------end
        //------------演示删除指定索引-------
        /*DeleteIndexRequestBuilder deleteRequestBuilder = client.admin().indices().prepareDelete("wesley6");
        DeleteIndexResponse deleteResponse = deleteRequestBuilder.execute().actionGet();
        System.out.println("------isDelete-----"+deleteResponse.isAcknowledged());*/
        //------------演示删除指定索引-------end
        //----------演示创建索引，类型
        // 使用XContentBuilder创建Mapping
        /*XContentBuilder builder = 
            XContentFactory.jsonBuilder()
                            .startObject()
                                .field("properties")
                                    .startObject()
                                        .field("name")
                                            .startObject()
                                                .field("index", "not_analyzed")
                                                .field("type", "string")
                                            .endObject()
                                        .field("age")
                                            .startObject()
                                                .field("index", "not_analyzed")
                                                .field("type", "integer")
                                            .endObject()
                                    .endObject()
                            .endObject();
        System.out.println(builder.string());  
        //创建索引
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("wesley00");
        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.execute().actionGet();
        System.out.println("------isCreated----"+createIndexResponse.isAcknowledged());
        //创建type
        PutMappingRequest mappingRequest = Requests.putMappingRequest("wesley00").source(builder).type("user");
        PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mappingRequest).actionGet();
        System.out.println("------isCreated----"+putMappingResponse.isAcknowledged());*/
        //----------演示创建索引，类型---end
        //---------演示更新文档----
        XContentBuilder builder = 
                XContentFactory.jsonBuilder()
                                .startObject()
                                    .field("author", "lisi")
                                    .field("title", "update111")
                                    .field("content","111111")
                                    .field("category","category-update")
                                .endObject();
        	UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate().setIndex("wesley").setType("test").setDoc(builder).setId("1111");
        	System.out.println("----------"+updateRequestBuilder);
        	UpdateResponse updateResponse = updateRequestBuilder.execute().actionGet();
        	System.out.println("---------"+updateResponse);
        	//说明isCreated这个方法如果返回的是true说明原来没有数据重新创建了，如果返回false说明更新成功。不知道他们为什么这么设计。
        	//Returns true if document was created due to an UPSERT operation 源码中的注释说明。
            System.out.println("---------"+updateResponse.isCreated()+"---version-"+updateResponse.getVersion()+"-------------"); //
        ////---------演示更新文档----end--
    }
}
