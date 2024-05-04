FROM debian:11-slim
RUN apt-get update && apt-get install -y nginx

VOLUME ["/var/www/html/" ]
COPY ./html /var/www/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]



