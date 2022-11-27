package org.spark;

import lombok.extern.log4j.Log4j2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.data.model.request.UserRequest;
import org.data.model.response.UserResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import scala.Tuple2;

import java.io.Serializable;
import java.util.UUID;

@Log4j2
public class UserCreteRequestSpark implements Serializable {
    public static void main(String[] args) {
        String file = "/home/ameya/Downloads/fbnames/names/xah";

        JavaSparkContext sc = sparkContext();

        String local = "http://localhost:9010/user/create";
        String remote = "http://192.168.0.104:90/user/create";
        String url = local;

        JavaRDD<UserRequest> requestRDD = userCreate(sc, file);
        requestRDD.mapToPair(request -> new Tuple2<>(request.getUsername(), request))
                //.repartitionAndSortWithinPartitions(new CustomPartitioner()) // creates requests with null values
                .filter(request -> request._2.getUsername() != null)
                .foreachPartition(partition -> {
                    RestTemplate restTemplate = new RestTemplate();
                    while (partition.hasNext()) {
                        UserRequest request = partition.next()._2;
                        UserResponse response = restTemplate.postForObject(url, new HttpEntity<>(request), UserResponse.class);
                        //log.info("Request: " + request + " Response: " + response);
                    }
                });
    }

    public static JavaRDD<UserRequest> userCreate(JavaSparkContext sc, String file) {
        return sc.textFile(file)
                .map(line -> line.split(" "))
                .filter(arr -> arr.length > 1)
                .map(arr -> clientRequest(arr));
    }

    public static UserRequest clientRequest(String... arr) {
        String firstName = arr[0];
        String lastName = arr[1];
        String username = firstName + UUID.randomUUID().toString().substring(0, 8);
        String email = firstName + "." + lastName + UUID.randomUUID().toString().substring(0, 8) + "@mail.com";

        return UserRequest.builder()
                .firstName(arr[0])
                .lastName(arr[1])
                .email(email)
                .username(username + UUID.randomUUID().toString().substring(0, 5))
                .build();
    }

    public static JavaSparkContext sparkContext() {
        SparkConf conf = new SparkConf().setAppName("First").setMaster("local[*]");
        return new JavaSparkContext(conf);
    }
}