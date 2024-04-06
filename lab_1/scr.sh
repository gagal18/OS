#! /bin/bash

if [ $# -lt 5 ]; then
	echo "More testing is neaded";
else	
	argv=("$@");
	sec=0;
	for num in {0..2}
	do
		sec=$((sec + argv[num]));
	done
	echo "Average execution time: $sec";
	echo "Count of executions: ${argv[$#]}";
	echo "The testing is done";
fi
