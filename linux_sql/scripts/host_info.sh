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
lscpu_out=$(lscpu)

#Retrieve hardware specifications variables
#xargs is a trick to trim leading and trailing white spaces
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$( echo "$lscpu_out"  | egrep "^Model name:" | awk '{for(i=3;i<=NF;++i)print $i}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | sed 's/.$//' | xargs)
total_mem=$(egrep "MemTotal:" /proc/meminfo | awk '{print $2}' | xargs)
timestamp=$(vmstat -t | awk '{print $18,$19}' | tail -n1 | xargs)

#PSQL command: Inserts server usage data into host_usage table
insert_stmt="INSERT INTO host_info(hostname,cpu_number,cpu_architecture,
            cpu_model, cpu_mhz, l2_cache,total_mem, timestamp) VALUES
          ('$hostname','$cpu_number','$cpu_architecture','$cpu_model',
          '$cpu_mhz','$L2_cache','$total_mem','$timestamp')"

#set up env var for psql
export PGPASSWORD=$psql_password

#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?