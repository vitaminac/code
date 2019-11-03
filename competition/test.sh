#!/bin/bash

rm *.bin
for file in *.cpp; do
    ./judge.sh $(basename -s .cpp $file) &
done

for py in $(find . -name "*.py"); do
    python $py &
done

wait