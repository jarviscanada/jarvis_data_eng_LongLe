#!/bin/bash

cmd=$1
db_username=$2
db_password=$3

sudo systemctl status docker || systemctl ...

docker container inspect jrvs-psql
container_status=$?

case $cmd in
   create)
   
   if [ $container_status -eq 0 ]; then
	echo 'Container already exists'
	exit 1
   fi

   if [ $# -ne 3 ]; then
	echo 'Create requires username and password'
	exit 1
   fi

   docker volume create jrvs-psql
   docker run jrvs-psql
   exit $?
   ;;

   start|stop)

   if [ $container_status -eq 1 ]; then
	exit 1
   fi
   ...

   docker container $cmd jrvs-psql
   exit $?
   ;;

   *)
   echo 'Illegal command'
   echo 'Commands: start|stop|create'
   exit 1
   ;;
esac

