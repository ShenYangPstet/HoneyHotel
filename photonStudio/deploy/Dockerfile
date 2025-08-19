FROM amazoncorretto:8u342
EXPOSE 9527
VOLUME ["/app/key","/app/file"]
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
WORKDIR /app
COPY PhotonStudio-2.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Xmx600m","-jar"]
CMD ["app.jar"]