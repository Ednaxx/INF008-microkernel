install:
	mvn install

clean:
	mvn clean

init:
	mvn exec:java -pl app

clean-init:
	mvn clean install exec:java -pl app