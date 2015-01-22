#!/bin/bash

for dimension in 2 3
do
	for count in 10000 25000 50000 75000 100000 250000 500000 750000 1000000 2500000
	do
		java -Xms2048m -Xmx3072m ru/spbau/ads/kozlov/rangeQueries/tests/NaiveSolutionTester $dimension $count 0.01f

		java ru/spbau/ads/kozlov/rangeQueries/tests/RangeTreeCorrectnessTester $dimension $count
		java -Xms2048m -Xmx3072m ru/spbau/ads/kozlov/rangeQueries/tests/RangeTreeTester $dimension $count 0.01f
	done
done
