#!/bin/bash

set -o errexit

for ((i = 0 ; i < 9 ; i++)); do
    if [ $i == 0 ]
    then
        python3 get_bmc_info.py "./brynmawr/data/Fall200$i.csv" "bmc_data/sf_F200$i.txt" "bmc_data/c_F200$i.txt"
    else
        python3 get_bmc_info.py "./brynmawr/data/Fall200$i.csv" "bmc_data/sf_F200$i.txt" "bmc_data/c_F200$i.txt"
        python3 get_bmc_info.py "./brynmawr/data/Spring200$i.csv" "bmc_data/sf_S200$i.txt" "bmc_data/c_S200$i.txt"
    fi
done

for ((i = 0 ; i < 5 ; i++)); do
    if [ $i == 4 ]
    then
        python3 get_bmc_info.py "./brynmawr/data/Spring201$i.csv" "bmc_data/sf_S201$i.txt" "bmc_data/c_S201$i.txt"
    else
        python3 get_bmc_info.py "./brynmawr/data/Fall201$i.csv" "bmc_data/sf_F201$i.txt" "bmc_data/c_F201$i.txt"
        python3 get_bmc_info.py "./brynmawr/data/Spring201$i.csv" "bmc_data/sf_S201$i.txt" "bmc_data/c_S201$i.txt"
    fi
done