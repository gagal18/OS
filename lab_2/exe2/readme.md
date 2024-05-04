# Задача 2
Потребно е да се направи апликација наречена City Noise Pollution. За таа цел има сенозори кои прибираат податоци за ниво на звук во одредена околина, се со цел да се мониторира бучава и дали таа е во склад со регулативите.

Барања на задачата:

Да се направат два Docker Compose сервиси. 

Да се креира Јава сервис SoundLevelSensor
запишува 10 нови бројки во датотека soundlevel.txt. 
Дали ќе ги запишувате бројките во датотеката со празно место или нов ред е оставено на Вас.
новите бројки се рандом генрирани во рангот [40, 100].
запишува нови бројки на секој 20 секунди.
 Да се креира Јава сервис SoundLevelMonitor 
прави average на вредностите од soundlevel.txt
запишува во датотека noisepollution.txt колкаво е нивото на Noise Pollution.
Дали ќе ги запишувате нивото во датотеката со празно место или нов ред е оставено на Вас.
нивото може да е Low, Medium и High. Low (40 dB to 60 dB), Medium (60 dB to 80 dB), High (над 80 dB)
запишува колкаво е нивото на секој 30 секунди.
Во формата поставете го излезот од командна линија на SoundLevelSensor, SoundLevelMonitor и дел од soundlevel.txt и noisepollution.txt.