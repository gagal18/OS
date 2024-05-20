FROM eclipse-temurin
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

RUN javac UDPClient.java
CMD ["tail", "-f", "/dev/null"]
