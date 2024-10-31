#! /usr/bin/python

import xml.etree.ElementTree as ET
import csv
import os

os.chdir("/work/diss/INDIRECT/Datasets/CCHIT/")
tree = ET.parse('target2.xml')
root = tree.getroot()

# open a file for writing

Resident_data = open('./low.csv', 'w')

# create the csv writer object

csvwriter = csv.writer(Resident_data)
resident_head = []
csvwriter.writerow(['id', 'text'])
count = 0
for member in root.findall('artifacts'):
    for artifact in member.findall('artifact'):
        id = artifact.find('id').text
        content = artifact.find('content').text
        csvwriter.writerow([id, content])

Resident_data.close()
