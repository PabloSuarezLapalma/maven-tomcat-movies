# Use a Maven base image with JDK 11 for the build stage
FROM maven:3.8.4-openjdk-11 as build

# Set the working directory
WORKDIR /usr/src/app

# Copy the pom.xml file
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application
COPY src/ ./src/

# Build the application
RUN mvn package

# Use a Tomcat base image for the final stage
FROM tomcat:10.0.16-jdk11-openjdk

# Remove the default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR from the build stage
COPY --from=build /usr/src/app/target/user-management-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the Tomcat port
EXPOSE 8080

# Run Tomcat
CMD ["catalina.sh", "run"]