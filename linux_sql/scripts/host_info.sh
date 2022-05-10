#! /bin/bash

#Setup arguments
psql_host=$1
port=$2

#validate arguments
if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

#parse hardware specification
hostname=$(hostname -f)
lscpu_out=`lscpu`
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)


#DO NOT put extra empty lines, it's ugly

#put appropriate exit number
exit 0
