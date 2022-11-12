FROM tomcat:9-jdk15-openjdk
ADD target/Docker_Container_Networking-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/app.war
EXPOSE 8080
CMD ["catalina.sh", "run"]

