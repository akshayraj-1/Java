#!/bin/bash

cd src
javac *.java

if [ $? -eq 0 ]; then
  java Main
else
  echo "Compilation failed."
fi
