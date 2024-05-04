#!/bin/bash
if [ ! -f $1 ]
then
	echo "Vnesete .json file kako argument!"
	exit -1
fi

if [ $# -ne 1 ]
then
	echo "Vnesete .json file kako argument!"
	exit -1
fi
if [ -f output.csv ]
then
	rm output.csv
fi
touch output.csv

json=$(cat $1)
filepaths=($(echo "$json" | grep -o '"filepath": "[^"]*' | sed 's/"filepath": "//'))
durations=($(echo "$json" | grep -o '"duration": [^,]*' | sed 's/"duration": //'))
filesizes=($(echo "$json" | grep -o '"filesize": "[^"]*' | sed 's/"filesize": "//'))

echo "id, filepath, filesize, is_longer" >> output.csv
sum=0
i=0;
for ((i = 0; i < ${#durations[@]}; i++)); do
	dur=${durations[i]}
	sum=$(awk "BEGIN {print $sum + $dur}")
done
avg=$(awk "BEGIN {print $sum / $i+1}")
for ((i = 0; i < ${#filepaths[@]}; i++)); do
	row=$(echo "${filepaths[i]}, ${durations[i]}, ${filesizes[i]}")
	#output+="$row\n"
	compare=$(awk "BEGIN {print ($avg >= ${durations[i]})}")
	if [ $compare -ne 1 ]; then
		echo "$row, 1" >> output.csv
	else
		echo "$row, 0 " >> output.csv
	fi
done
