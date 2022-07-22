.PHONY: build run

all: build run	

# build the docker image with name 'java-a5'
build:
	docker build -t java-a5 . 

# drops you into the container w/ built jar
run-interactive: 
	docker run -it --rm java-a5 bash

# runs the gui over X11 (assuming you have X11 installed and running on your machine)
run-gui:
	docker run -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=${HOSTNAME}:0 -v ${HOME}/.Xauthority:/root/.Xauthority java-a5

run-og: 
	docker run -t --rm java-a5 sh -c 'java -jar drone_delivery.jar < commands_35.txt'

run-locally:
	find app -name "*.java" | xargs javac -d ./target
	jar cfe drone_delivery.jar Sample_A5_Frame -C ./target .
	java -jar drone_delivery.jar