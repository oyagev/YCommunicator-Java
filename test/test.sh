#! /bin/bash

export CLASSPATH=/usr/share/java/junit4.jar:../bin
javac -d ../bin *.java ; java org.junit.runner.JUnitCore YCommunicatorTest
