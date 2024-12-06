1.模拟简单注册
curl --location 'http://localhost:8080/user/register' \
--header 'Content-Type: application/json' \
--data '{
"userName":"luochao",
"password":"luochao",
"phoneNumber":"15620691809"
}'
2模拟登录
curl --location 'http://localhost:8080/user/register' \
--header 'Content-Type: application/json' \
--data '{
"userName":"luochao",
"password":"luochao",
"phoneNumber":"15620691809"
}'
3.模拟chat
curl --location 'http://localhost:8080/OpenAi/ai/chat?msg=%E5%A4%A9%E6%B0%94'
4.模拟通过token获取用户信息
curl --location 'http://localhost:8080/user/getUser' \
--header 'token: 1000001562069180935a7c3de91704194a10f7fc6fa678f0b' \
--header 'Content-Type: application/json' \
--data '{}'

5.模拟并发情况下单扣减库存
http://localhost:8080/order/addOrder
可通过jemter调用多个服务进行测试
6.模拟取消订单
curl --location 'http://localhost:8080/order/cancelOrder' \
--header 'token: 1000001562069180935a7c3de91704194a10f7fc6fa678f0b' \
--header 'Content-Type: application/json' \
--data '{
"id":"1"
}'
