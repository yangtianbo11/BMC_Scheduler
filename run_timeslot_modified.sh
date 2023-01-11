#!/bin/bash

set -o errexit
DIR="bmc_data_timeslot_splitted"
for ((i = 0 ; i < 9 ; i++)); do
    if [ $i == 0 ]
    then
        java Main.java "$DIR/c_F200$i.txt" "$DIR/sf_F200$i.txt" "$DIR/output_F200$i.txt"
    else
        java Main.java "$DIR/c_F200$i.txt" "$DIR/sf_F200$i.txt" "$DIR/output_F200$i.txt"
        java Main.java "$DIR/c_S200$i.txt" "$DIR/sf_S200$i.txt" "$DIR/output_S200$i.txt"
    fi
done

for ((i = 0 ; i < 5 ; i++)); do
    if [ $i == 4 ]
    then
        java Main.java "$DIR/c_S201$i.txt" "$DIR/sf_S201$i.txt" "$DIR/output_S201$i.txt"
    else
        java Main.java "$DIR/c_F201$i.txt" "$DIR/sf_F201$i.txt" "$DIR/output_F201$i.txt"
        java Main.java "$DIR/c_S201$i.txt" "$DIR/sf_S201$i.txt" "$DIR/output_S201$i.txt"
    fi
done