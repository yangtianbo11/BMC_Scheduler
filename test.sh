#!/bin/bash




for ((i=0;i<5;i++))
do
    java Main.java "$1/constraints_$i" "$1/prefs_$i" "$1/schedule_$i"
    
done

