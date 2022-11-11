package org.spark;

import lombok.extern.log4j.Log4j2;
import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.data.model.request.UserClientRequest;
import org.data.model.response.UserClientResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import scala.Tuple2;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

@Log4j2
public class UserRequestSpark implements Serializable {
    public static void main(String[] args) {
        String file = "/home/ameya/Downloads/fbnames/names/xah";

        JavaSparkContext sc = sparkContext();

        String local = "http://localhost:9010/user/client/create";
        String remote = "http://192.168.0.104/user/client/create";
        String url = remote;

        JavaRDD<UserClientRequest> requestRDD = userCreate(sc, file);
        requestRDD
                .mapToPair(request -> new Tuple2<>(request.getUsername(), request))
                //.repartitionAndSortWithinPartitions(new CustomPartitioner()) // creates requests with null values
                .filter(request -> request._2.getUsername() != null)
                .foreachPartition(partition -> {
            RestTemplate restTemplate = new RestTemplate();
            while (partition.hasNext()) {
                UserClientRequest request = partition.next()._2;
                UserClientResponse response = restTemplate.postForObject(url, new HttpEntity<>(request), UserClientResponse.class);
                //log.info("Request: " + request + " Response: " + response);
            }
        });
    }

    public static JavaRDD<UserClientRequest> userCreate(JavaSparkContext sc, String file) {
        return sc.textFile(file)
                .map(line -> line.split(" "))
                .filter(arr -> arr.length > 1)
                .map(arr -> clientRequest(arr));
    }

    public static UserClientRequest clientRequest(String... arr) {
        String firstName = arr[0];
        String lastName = arr[1];
        String username = firstName + UUID.randomUUID().toString().substring(0, 8);
        String email = firstName + "." + lastName + UUID.randomUUID().toString().substring(0, 8) + "@mail.com";

        return UserClientRequest.builder()
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

class CustomPartitioner extends Partitioner {

    @Override
    public int numPartitions() {
        return 10;
    }

    @Override
    public int getPartition(Object key) {
        return Math.abs(key.hashCode()) % numPartitions();
    }
}
