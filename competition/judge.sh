#!/bin/bash

base=$(basename -s .cpp "$1")
# echo $base
dirn=$(dirname "$1")
# echo $dirn
g++ -I . -g -o $1.bin $1
inputs=$(find "$dirn" -name "$base*.in")
# echo $inputs
for input in $inputs; do
    # echo $input
    output="$dirn/$(basename -s .in $input).out"
    # echo $output
    out=$($1.bin < $input)
    echo "$out" | cmp $output || echo "stdout is different than $output" && echo "$out"
done