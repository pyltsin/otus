#!/usr/bin/env bash
#Запуск с UseG1GC

MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"
GC="-XX:+UseG1GC"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

java $MEMORY $GC $DUMP -XX:OnOutOfMemoryError="kill -3 %p" -jar target/HW4.jar > jvmG1GC.out
