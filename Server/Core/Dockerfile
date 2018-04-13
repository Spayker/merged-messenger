# use maven jdk image to build jar file of an application
FROM maven:3.5.0-jdk-8
# set the working directory to /app folder
WORKDIR /app
# copy pom.xml to /app or workdir
COPY pom.xml .
# copy sources to current workdir
COPY src ./src
# run maven plugin with option springboot: 
# run which refers to springboot maven plugin configured beforehand in pom.xml
ENTRYPOINT ["mvn","spring-boot:run"]