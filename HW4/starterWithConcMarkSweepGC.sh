#!/usr/bin/env bash
#Запуск с ConcMarkSweepGC для Old, ParNewGC - для Young
#CMSParallelRemarkEnabled - параллельно основному потоку
#UseCMSInitiatingOccupancyOnly , CMSInitiatingOccupancyFraction - указание части от общей памяти
# ScavengeBeforeFullGC -collect young generation before doing Full GC
# CMSScavengeBeforeRemark - Instructs garbage collector to collect young generation before doing CMS remark phase
# UseParNewGC - для YONG

MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"

GC="-XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark -XX:+UseParNewGC"

DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

java $MEMORY $GC $DUMP -XX:OnOutOfMemoryError="kill -3 %p" -jar target/HW4.jar > jvmConcMarkSweepGC.out
