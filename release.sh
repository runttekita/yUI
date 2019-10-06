#!/bin/bash

mvn clean
mvn package
mv ./mods/yUi.jar ./workshop/content/
cp ./workshop/content/yUi.jar ./workshop/content/idea/yUi.jar
zip -d ./workshop/content/idea/yUi.jar kotlin/*
java -jar ../.local/share/Steam/steamapps/common/SlayTheSpire/mod-uploader.jar upload -w=workshop