#!/bin/bash

g++ -g -o $(basename -s .cpp $1).bin $1.cpp
for input in $(find . -name "$1*.in"); do
    echo $input
    output=$(basename -s .in $input).out
    ./$(basename -s .cpp $1).bin < $input > answer.out
    out=$(cat answer.out)
    echo "$out" | cmp $output || echo "stdout is different than $output" && echo "$out"
done