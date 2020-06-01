package com.demo;

import com.alibaba.fastjson.JSON;
import com.demo.entity.User;
import lombok.val;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * 测试ES 7.6.1 API
 */
@SpringBootTest
class EsApiDemoApplicationTests {

    /**
     * 客户端对象
     */
    @Autowired
    @Qualifier("restHighLevelClient") //简化
    private RestHighLevelClient client;

    //测试操作创建索引
    @Test
    void testCreteIndex() throws IOException {
        //1、创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("test01");
        //2.客户端执行请求IndexClient,请求后获得响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    //判断索引是否存在
    @Test
    void testExistsIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("test01");
        boolean boo = client.indices().exists(request,RequestOptions.DEFAULT);
        System.out.println(boo);
    }

    //测试操作删除索引
    @Test
    void testDeleteIndex() throws IOException {
        //1、创建索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("test2");
        //2.客户端执行请求IndexClient,请求后获得响应
        AcknowledgedResponse response = client.indices().delete(request,RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    //测试添加文档
    @Test
    void testAddDocument() throws IOException {
        //创建对象
        User user = new User("张三",18);
        //获取请求对象
        IndexRequest request = new IndexRequest("test01");
        //设置请求规则 （PUT /test1/_doc/1）
        request.id("1");
        request.timeout("1s"); //设置超时时间为1秒
        //将数据放入请求
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求
        IndexResponse response = client.index(request,RequestOptions.DEFAULT);
        System.out.println(request);
    }
    

}
