FROM eclipse-temurin
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

RUN javac UDPServer.java
CMD ["java", "UDPServer"]