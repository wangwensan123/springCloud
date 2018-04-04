package com.example.demo.controller;
/**
 *@auth wws
 *@date 2018年4月3日---下午4:52:41
 *
 **/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.domain.Book;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sang on 2017/9/9.
 */
@RestController
public class ConsumerBookController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/gethello")
    public String getHello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://SPRINGCLOUDPROVIDER/hello", String.class);
        String body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders headers = responseEntity.getHeaders();
        StringBuffer result = new StringBuffer();
        result.append("responseEntity.getBody()：").append(body).append("<hr>")
        .append("responseEntity.getStatusCode()：").append(statusCode).append("<hr>")
        .append("responseEntity.getHeaders()：").append(headers).append("<hr>");
        return result.toString();
    }
    @RequestMapping("/sayhello")
    public String sayHello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://SPRINGCLOUDPROVIDER/sayhello?name={1}", String.class, "张三");
        return responseEntity.getBody();
    }
    @RequestMapping("/sayhello2")
    public String sayHello2() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "李四");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://SPRINGCLOUDPROVIDER/sayhello?name={name}", String.class, map);
        return responseEntity.getBody();
    }
    @RequestMapping("/sayhello3")
    public String sayHello3() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://SPRINGCLOUDPROVIDER/sayhello?name={name}").build().expand("王五").encode();
        URI uri = uriComponents.toUri();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        return responseEntity.getBody();
    }
    @RequestMapping("/book1")
    public Book book1() {
        ResponseEntity<Book> responseEntity = restTemplate.getForEntity("http://SPRINGCLOUDPROVIDER/getbook1", Book.class);
        return responseEntity.getBody();
    }
    @RequestMapping("/book2")
    public Book book2() {
        Book book = restTemplate.getForObject("http://SPRINGCLOUDPROVIDER/getbook1", Book.class);
        return book;
    }
    @RequestMapping("/book3")
    public Book book3() {
        Book book = new Book();
        book.setName("红楼梦");
        ResponseEntity<Book> responseEntity = restTemplate.postForEntity("http://SPRINGCLOUDPROVIDER/getbook2", book, Book.class);
        return responseEntity.getBody();
    }
    @RequestMapping("/put")
    public void put() {
        Book book = new Book();
        book.setName("红楼梦");
        restTemplate.put("http://SPRINGCLOUDPROVIDER/getbook3/{1}", book, 99);
    }
    @RequestMapping("/delete")
    public void delete() {
        restTemplate.delete("http://SPRINGCLOUDPROVIDER/getbook4/{1}", 100);
    }
}
