#!/bin/bash

rm $(find . -name "*.bin")
for file in $(find . -name "*.cpp"); do
    ./judge.sh $file &
done

for py in $(find . -name "*.py"); do
    python $py &
done

wait