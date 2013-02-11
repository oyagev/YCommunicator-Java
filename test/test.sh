#! /bin/bash

export CLASSPATH=/usr/share/java/junit4.jar:../bin
javac -d ../bin ..src/*.java *.java ; java org.junit.runner.JUnitCore oyagev.YCommunicator.YCommunicatorTest
