#!/usr/bin/env bash
#Запуск с стандартными параметрами

MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

java $MEMORY $DUMP -XX:OnOutOfMemoryError="kill -3 %p" -jar target/HW4.jar > jvmStandart.out
