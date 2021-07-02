files = ['IsEmpty.csv', 'IsSimple.csv', 'IsForest.csv', 'IsTree.csv', 'HasCycle.csv', 'IsConnected.csv', "IsComplete.csv"]

# Graph name, Java time, Groove think time, Groove time

for file in files:
	with open(file, 'r') as f:
		
		lines = f.readlines()
		
		first = lines[0].split(",")
		leastFactor = maxFactor = totalFactor = int(first[3]) / int(first[1]) 
		for i in range(1, len(lines)):
			line = lines[i].split(",")
			factor = int(line[3]) / int(line[1])
			leastFactor = min(leastFactor, factor)
			maxFactor = max(maxFactor, factor)
			totalFactor += factor
		totalFactor /= len(lines)

		print(f'File: {file}: {leastFactor}, {totalFactor}, {maxFactor}')

