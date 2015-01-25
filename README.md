# JavaEE2014
MySQL script for token strore in database:
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
)

cURL OAUth 2.0 link for inMemory user:
curl -vu clientapp:123456 -H "Accept: application/json" "http://localhost:8080/javaee2014/oauth/token" -d "password=123456&username=mkyong&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
