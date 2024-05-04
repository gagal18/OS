FROM eclipse-temurin

WORKDIR /usr/src/java
COPY ./java .

# VOLUME ["/var/www/html/" ]
# COPY ./html /var/www/html/
# If the variable is passed from Dockerfile
# ENV PRODUCT_FILE_PATH=/var/www/html/products.txt

RUN javac ProductsManagement.java
CMD ["java", "ProductsManagement"]