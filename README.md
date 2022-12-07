## User Service

### Description

*   Service to create, store and retrieve users.
*   Provides an API to create and get users.

### Implementation

*   Create User
*   While creating the users, once the information is stored in a relational database, it is stored in a cache.
*   For any updates along with the database, the cache is updated.
*   The cache is distributed across other services, through which the user can be fetched.

### Tech Stack

*   **Language**
    *   Java 11
*   **API**
    *   Spring Boot
        *   Spring Boot Web
        *   Spring Boot Webflux
    *   Reactor Core
    *   Lombok
    *   Log4j2
*   **Database**
    *   TimescaleDB
        *   Logging events
    *   Elasticsearch
        *   Logging response post spark testing
*   **Deployment**
    *   Docker
    *   Kubernetes
*   **Load Testing tools**
    *   JMeter
    *   Apache Spark

### Architecture Diagram

![](https://user-images.githubusercontent.com/117295903/206179875-9cf0a258-c201-4378-a72a-98c9190cfad6.jpeg)

### Load Tests Result

#### Spark Test

*   Tested with 100K records.
*   Configuration
    *   16 core CPU 16 GB RAM
*   Was able to fetch user on average 200 records per second, with per record process time of 5 ms

| request\_time | requests\_per\_second | avg\_process\_time\_in\_ms |
| --- | --- | --- |
| 2022-11-27 22:42:08.000 | 104 | 5.5384615384615385 |
| 2022-11-27 22:42:07.000 | 190 | 5.0000000000000000 |
| 2022-11-27 22:42:06.000 | 188 | 5.2127659574468085 |
| 2022-11-27 22:42:05.000 | 200 | 5.8600000000000000 |
| 2022-11-27 22:42:04.000 | 186 | 5.6935483870967742 |
| 2022-11-27 22:42:03.000 | 200 | 4.9500000000000000 |
| 2022-11-27 22:42:02.000 | 220 | 4.9772727272727273 |
| 2022-11-27 22:42:01.000 | 216 | 5.7129629629629630 |
| 2022-11-27 22:42:00.000 | 232 | 6.5818965517241379 |
| 2022-11-27 22:41:59.000 | 220 | 4.8136363636363636 |
| 2022-11-27 22:41:58.000 | 196 | 4.9489795918367347 |
| 2022-11-27 22:41:57.000 | 166 | 5.9939759036144578 |
| 2022-11-27 22:41:56.000 | 182 | 5.3351648351648352 |
| 2022-11-27 22:41:55.000 | 212 | 6.3679245283018868 |
| 2022-11-27 22:41:54.000 | 202 | 6.0297029702970297 |
| 2022-11-27 22:41:53.000 | 198 | 4.9898989898989899 |
| 2022-11-27 22:41:52.000 | 130 | 4.4230769230769231 |
| 2022-11-27 22:41:51.000 | 95 | 3.5578947368421053 |
| 2022-11-27 22:41:50.000 | 110 | 5.2727272727272727 |
| 2022-11-27 22:41:49.000 | 106 | 6.5471698113207547 |

*   Was able to create user on average of 90 records per second, with each record taking an average of 55 ms.

| request\_time | requests\_per\_second | avg\_process\_time\_in\_ms |
| --- | --- | --- |
| 2022-11-27 22:43:15.000 | 81 | 55.1975308641975309 |
| 2022-11-27 22:43:14.000 | 100 | 48.4600000000000000 |
| 2022-11-27 22:43:13.000 | 94 | 50.2446808510638298 |
| 2022-11-27 22:43:12.000 | 75 | 65.5333333333333333 |
| 2022-11-27 22:43:11.000 | 105 | 44.1238095238095238 |
| 2022-11-27 22:43:10.000 | 89 | 55.1573033707865169 |
| 2022-11-27 22:43:09.000 | 93 | 50.6774193548387097 |
| 2022-11-27 22:43:08.000 | 84 | 57.7023809523809524 |
| 2022-11-27 22:43:07.000 | 91 | 53.4615384615384615 |
| 2022-11-27 22:43:06.000 | 98 | 49.2857142857142857 |
| 2022-11-27 22:43:05.000 | 104 | 45.0576923076923077 |
| 2022-11-27 22:43:04.000 | 77 | 64.2857142857142857 |
| 2022-11-27 22:43:03.000 | 86 | 55.1627906976744186 |
| 2022-11-27 22:43:02.000 | 75 | 64.6533333333333333 |
| 2022-11-27 22:43:01.000 | 78 | 61.6282051282051282 |
| 2022-11-27 22:43:00.000 | 83 | 58.3253012048192771 |
| 2022-11-27 22:42:59.000 | 86 | 55.6976744186046512 |
| 2022-11-27 22:42:58.000 | 80 | 60.9500000000000000 |
| 2022-11-27 22:42:57.000 | 77 | 62.2077922077922078 |
| 2022-11-27 22:42:56.000 | 90 | 54.0222222222222222 |
