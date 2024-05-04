# Задача 1
Задачите вклучуваат создавање и управување со контејнери и слики на Docker за да се постави едноставен веб-сервер кој хостира текстуална датотека. Прво, интерактивно ќе креирате Docker контејнер наречен nginx-product-host-i кој работи на веб-сервер nginx, дозволувајќи им на портата 8080 на домаќинот да пристапи до портата 80 на контејнерот. Следно, ќе создадете слика на Docker со име nginx-product-host користејќи Dockerfile и ќе го повторите процесот на создавање контејнер, овој пат дозволувајќи пристап преку портата 8081. Откако рачно ќе копирате датотека во контејнерите, ќе ја потврдите пристапноста преку барањата HTTP . Потоа, ќе престанете и ќе ги отстраните контејнерите, ќе креирате нов директориум, ќе копирате датотека во него и ќе создадете друг nginx контејнер со волумен мапиран во директориумот на домаќинот. Конечно, ќе ја контејнеризирате Java апликацијата користејќи Dockerfile, дозволувајќи и да ја прикаже содржината на копираната датотека и подоцна да ја менувате за да користи променливи на околината за флексибилност.

Steps:
1. Креирање интерактивно на слика и стартување на контејнер со таа слика
```
    1623  docker run -it --name nginx-prep debian:11-slim

    1624  winpty docker run -it --name nginx-prep debian:11-slim (winpty е префикс бидејќи користам bash i zsh)

    Во интерактивниот дел:

    apt-get update

    apt-get install -y ngnix

    1625  docker commit nginx-prep nginx-product-host-i
```


2. Стартување на контејнер со сликата која ја зачувавме
```
    1626  winpty docker run -d -p 8080:80 --name nginx-product-host-i-container nginx-product-host-i bash -c "nginx -g 'daemon off;'"`

```



3. Креирање на Dockerfile и слика со тој Dockerfile
```
    1681  docker build -t nginx-product-host-i .`
```

4. Креирање на контејнер

```
    1682  docker run -d -p 8081:80 --name nginx-product-host-container nginx-product-host-i
```
5. Копирање на products.txt

```
    1689  docker cp ./products.txt nginx-product-host-container:/var/www/html/

    1690  docker cp ./products.txt nginx-product-host-i-container:/var/www/html/
```

6. Валидација на products.txt, истото може да се валидира и преку browser бидејќи користи GET method

 1692  curl -I http://localhost:8081/products.txt

        % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current

                                        Dload  Upload   Total   Spent    Left  Speed

        0    43    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0HTTP/1.1 200 OK

        Server: nginx/1.18.0

        Date: Sat, 04 May 2024 10:37:37 GMT

        Content-Type: text/plain

        Content-Length: 43

        Last-Modified: Fri, 03 May 2024 17:14:15 GMT

        Connection: keep-alive

        ETag: "66351b67-2b"

        Accept-Ranges: bytes



 1693  curl -I http://localhost:8080/products.txt

        % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current

                                        Dload  Upload   Total   Spent    Left  Speed

        0    43    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0HTTP/1.1 200 OK

        Server: nginx/1.18.0

        Date: Sat, 04 May 2024 10:38:23 GMT

        Content-Type: text/plain

        Content-Length: 43

        Last-Modified: Fri, 03 May 2024 17:14:15 GMT

        Connection: keep-alive

        ETag: "66351b67-2b"

        Accept-Ranges: bytes

7.  Стопирање и бришење
```
    1697  docker stop nginx-product-host-i-container

    1698  docker remove nginx-product-host-i-container

    1699  docker stop nginx-product-host-container

    1700  docker remove nginx-product-host-container
```
8. Креирав нов DIR html во мојот проект, products.txt го ставив таму
9. Изменав во Dockerfile. Ги додадов следните линии

    VOLUME ["/var/www/html/" ]
    COPY ./html /var/www/html/
10. Валидација на products.txt, истото може да се валидира и преку browser бидејќи користи GET method

    ➜  exe1 git:(main) ✗ curl -I http://localhost:8081/products.txt

    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current

                                    Dload  Upload   Total   Spent    Left  Speed

    0    43    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0HTTP/1.1 200 OK

    Server: nginx/1.18.0

    Date: Sat, 04 May 2024 10:54:20 GMT

    Content-Type: text/plain

    Content-Length: 43

    Last-Modified: Fri, 03 May 2024 17:14:15 GMT

    Connection: keep-alive

    ETag: "66351b67-2b"

Accept-Ranges: bytes



11. Креирав нов Dockerfile.java, додадов нов директориум /java каде што го ставив java кодот, креирав слика, па потоа контеинер.

13. Мапирав volume со фајлот

14. Листањето беше успешно
    ```

    1717  docker build -t products-viewer -f Dockerfile.java .

    1720  docker run -p 8080:80 products-viewer
    ```


    Product Name: Fridge

    Product Price: $600

    Product Quantity: 5


    Product Name: TV

    Product Price: $1500

    Product Quantity: 6


    Product Name: Microwave

    Product Price: $200

    Product Quantity: 10



15. Додадов ЕNV var во Dockerfile.java изменав кодот во ProductsManagment.java, го стартував контејнерот и беше успешно


    Product Name: Fridge

    Product Price: $600

    Product Quantity: 5


    Product Name: TV

    Product Price: $1500

    Product Quantity: 6


    Product Name: Microwave

    Product Price: $200

    Product Quantity: 10



16. Стопирав контејнер, тргнав кодот во Dockerfile.java да нема ЕNV var. И го стартовав контејнерот со -е i -v.
```
    docker run -v ./html:/var/www/html/ -e PRODUCT_FILE_PATH="/var/www/html/products.txt" --name products-viewer-container products-viewer
```


17. Излезот е истиот

    Product Name: Fridge

    Product Price: $600

    Product Quantity: 5



    Product Name: TV

    Product Price: $1500

    Product Quantity: 6



    Product Name: Microwave

    Product Price: $200

    Product Quantity: 10
