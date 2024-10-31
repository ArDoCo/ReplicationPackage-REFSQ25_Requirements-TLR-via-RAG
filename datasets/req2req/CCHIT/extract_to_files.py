# Content of CSVs:
# id,text
# 1674,The system shall improve accessibility of online clinical information and results.

## To Files in ./low and ./high


def convert_text(text:str):
    text = text.strip()
    if text.startswith("\""):
        text = text[1:]
    if text.endswith("\""):
        text = text[:-1]
    return text

with open('low.csv') as low:
    low_data = low.read().splitlines()

with open('high.csv') as high:
    high_data = high.read().splitlines()


for i in range(1, len(high_data)):
    if(len(high_data[i].strip()) == 0):
        continue
    split = high_data[i].split(',',maxsplit=1)
    if len(split) != 2:
        print(f'Error: {split}')
        continue
    id = split[0]
    text = split[1]
    with open(f'./high/{id}', 'w') as high_file:
        high_file.write(convert_text(text))

for i in range(1, len(low_data)):
    if(len(low_data[i].strip()) == 0):
        continue
    split = low_data[i].split(',',maxsplit=1)
    if len(split) != 2:
        print(f'Error: {split}')
        continue
    id = split[0]
    text = split[1]
    with open(f'./low/{id}', 'w') as low_file:
        low_file.write(convert_text(text))