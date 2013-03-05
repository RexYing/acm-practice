#!/bin/sh  

BIN_PATH=bin

dir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
echo "running/timing the program ZombieBlast_K (Pacific Northwest 2010)"
echo directory:
echo $dir
echo
time java -classpath $BIN_PATH pacnorthwest.acm10.ZombieBlast_K < $dir/IO/k.in

