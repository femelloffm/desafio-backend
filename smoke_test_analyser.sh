#!/bin/bash

input_file="$HOME/data/in/smoke_test.dat"
number_of_files=$(ls $HOME/data/out | wc -l)

echo -e "001ç77745678912çJoseç48000" > $input_file
sleep 1

if [[ $(ls $HOME/data/out | wc -l) > $number_of_files ]]; then
    echo "Success: a new report was created"
else
  	echo "Test failed: no output was created"
fi

rm $input_file