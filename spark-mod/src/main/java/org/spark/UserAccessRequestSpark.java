package org.spark;

import lombok.extern.log4j.Log4j2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.data.model.request.UserRequest;
import org.data.model.response.UserResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Log4j2
public class UserAccessRequestSpark {
    public static void main(String[] args) {
        String file = "/home/ameya/user_ids.csv";

        JavaSparkContext sc = sparkContext();

        String local = "http://localhost:9010/user/";
        String remote = "http://192.168.0.104:90/user/";
        String url = local;

        JavaRDD<String> idRdd = userIds(sc, file);
        idRdd.foreachPartition(idPartition -> {
            RestTemplate restTemplate = new RestTemplate();
            while (idPartition.hasNext()) {
                String id = idPartition.next();
                UserResponse response = restTemplate.getForObject(new URI(url + id), UserResponse.class);
                //log.info(id + " " + response);
            }
        });
    }

    public static JavaRDD<String> userIds(JavaSparkContext sc, String file) {
        return sc.textFile(file)
                .map(line -> line.replace(",", ""));
    }

    public static JavaSparkContext sparkContext() {
        SparkConf conf = new SparkConf().setAppName("First").setMaster("local[*]");
        return new JavaSparkContext(conf);
    }
}
