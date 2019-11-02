#!/bin/bash

rm *.bin
for file in *.cpp; do
    ./judge.sh $(basename -s .cpp $file)
done