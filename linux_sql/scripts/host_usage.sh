#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
  echo "Illegal number of arguments"
  exit 1
fi

#Save machine statistics in MB and current machine hostname to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

#Retrieve hardware specifications variables
#xargs is a trick to trim leading and trailing white spaces
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" |  awk '{print $15}' | tail -n1 | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $13}'| tail -n1 | xargs)
disk_io=$(vmstat -d | awk '{print $10}' | tail -n1 | xargs)
disk_available=$(df -BM / | awk '{print $4}' | tail -n1 | sed 's/.$//' | xargs)
timestamp=$(vmstat -t | awk '{print $18,$19}' | tail -n1 | xargs)


#Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

#PSQL command: Inserts server usage data into host_usage table
insert_stmt="INSERT INTO host_usage(timestamp,host_id,memory_free,
             cpu_idle,cpu_kernel,disk_io,disk_available)
             VALUES ('$timestamp',$host_id,'$memory_free','$cpu_idle',
             '$cpu_kernel','$disk_io','$disk_available')"

#set up env var for psql
export PGPASSWORD=$psql_password

#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?