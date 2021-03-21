From openjdk:8
RUN mkdir /var/lib/app
COPY . /var/lib/app
COPY /target/Spring-Data-Elasticsearch-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "app.jar"]
CMD ["-start"]
