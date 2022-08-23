#! /bin/bash

#Script to Create, Start and Stop the PSQL Container

#Check docker status, start if not alreday running
systemctl status docker || systemctl start docker
docker pull postgres

#Create jrvs-psql container
if [ "$1" == "create" ]; then

   #Check if container is already created
   run_stat=$(docker container ls -a -f name=jrvs-psql | wc -l)
   if [ "$run_stat" == "2" ]; then
      echo "The jrvs-psql Container is Already Created!" 1>&2
      exit 1
   fi

   #Check username and password parameter
   if [ "$2" == "" ] || [ "$3" == "" ]; then
      echo "Missing Username or Password!" 1>&2
      exit 1
   fi

   docker volume create pgdata
   docker run --name jrvs-psql -e POSTGRES_PASSWORD=$3 -e POSTGRES_USER=$2 -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
   exit 0
fi

#Check if Container has been created
run_stat2=$(docker container ls -a -f name=jrvs-psql | wc -l)
if [ "run_stat2" == "1" ]; then
  echo "jrvs-psql Container Has Not Been Created!" 1>&2
  exit 1
fi

#Start docker container
if [ "$1" == "start" ]; then
   docker container start jrvs-psql
   exit 0
fi

#Stop docker container
if [ "$1" == "stop" ]; then
   docker container stop jrvs-psql
   exit 0
fi