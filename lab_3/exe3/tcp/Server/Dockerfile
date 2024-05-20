FROM eclipse-temurin
COPY ./Server.java /usr/src/myapp/tcp/Server/
WORKDIR /usr/src/myapp/tcp/Server

RUN javac Server.java
CMD ["java", "Server"]