#!/bin/bash
if [ $# -ne 1 ]
then
    echo "Vnesete papka kako argument"
    exit -1
fi
if [ ! -d $1 ]
then
    echo "Vnesete papka kako argument"
    exit -1
fi
if [ -f total.rez ]
then
        rm total.rez
fi
touch total.rez

if [ -f results.rez ]
then
        rm results.rez
fi
touch results.rez
dirs=$(ls $1)
unique_teams=()
for dir in $dirs
do 
 	full_path="$1/$dir/$dir.rez"
	teams=`cat $full_path | grep -v "^l" | awk -F, '{print $2}'`
	echo "$teams" >> total.rez
	unique_teams=$(sort total.rez | uniq)
done
for team in $unique_teams
	do
		points=$(grep "$team" total.rez | wc -l)
		echo $points $team >> results.rez
	done

