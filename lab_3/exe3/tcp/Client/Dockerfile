FROM eclipse-temurin
COPY ./Client.java /usr/src/myapp/tcp/Client/
WORKDIR /usr/src/myapp/tcp/Client

RUN javac Client.java
CMD ["tail", "-f", "/dev/null"]
