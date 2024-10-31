#! /usr/bin/python

import os
import csv

dirpath = '/work/diss/INDIRECT/Datasets/CM1Dataset/low/'
output = 'output_file.csv'
with open(output, 'w') as outfile:
    csvout = csv.writer(outfile)
    csvout.writerow(['id', 'text'])

    files = os.listdir(dirpath)

    for filename in files:
        with open(dirpath + '/' + filename) as afile:
            lines = [ ]
            for line in afile.readlines():
                lines.append(line.decode(encoding='utf-8',errors='ignore').strip())
            csvout.writerow([str(filename), ' '.join(lines)])
            afile.close()
    outfile.close()
                                                            
