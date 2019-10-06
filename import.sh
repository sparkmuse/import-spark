#!/bin/bash

echo "Downloading the files..."
curl -O "https://storage.googleapis.com/freebase-public/deleted_freebase.tar.gz"

echo "Unpacking the files..."
tar -zxvf deleted_freebase.tar.gz

echo "Concatenating all the files..."
cat deletions/* >  full-data.csv

echo "Creating sample data..."
head deletions/deletions.csv-00000-of-00020 > sample-data.csv