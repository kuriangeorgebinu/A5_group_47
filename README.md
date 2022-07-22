required java jdk 17

# RUN

- `find app -name "*.java" | xargs javac -d ./target`
- `jar cfe drone_delivery.jar Sample_A5_Frame -C ./target .`
- `java -jar drone_delivery.jar`
