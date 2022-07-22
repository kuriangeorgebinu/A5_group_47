# Compile our java files in this container
FROM openjdk:17-slim AS builder
COPY app/ /usr/src/cs6310/app
WORKDIR /usr/src/cs6310
RUN find app/ -name "*.java" | xargs javac -d ./target
RUN jar cfe drone_delivery.jar Sample_A5_Frame -C ./target/ .

# Copy the jar and test scenarios into our final image
FROM openjdk:17.0-oraclelinux8 
WORKDIR /usr/src/cs6310
RUN microdnf install -y libXext.x86_64
RUN microdnf install -y libXrender.x86_64
RUN microdnf install -y libXtst.x86_64
#COPY test_scenarios ./
#COPY test_results ./
COPY --from=builder /usr/src/cs6310/drone_delivery.jar ./drone_delivery.jar
CMD ["java", "-jar", "drone_delivery.jar", "commands_00.txt"]
