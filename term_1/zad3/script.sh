#!/bin/bash
if [ $# -ne 2 ]
then
	echo "Potrebni se dva argumenti" 
	exit -1
fi
firstMonth=$(grep "$1" input.csv | awk -F "|" '{print $0}' | sed 's/ /_/g')
secondMonth=$(grep "$2" input.csv | awk -F "|" '{print $0}' | sed 's/ /_/g')
echo "$secondMonth"
for row in $secondMonth
do 
	echo "$row"
done


