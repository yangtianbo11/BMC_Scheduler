import re
f = open('bmc_data/record.txt', 'r')
line = f.readline()
totalPercent = 0
totalTime = 0
count = 0
while line != '':
    if re.match('Fit percentage:', line):
        count = count + 1
        totalPercent = totalPercent + float(line.split()[2].replace('%',''))
    if re.match('Time used', line):
        totalTime = totalTime + float(line.split()[2])
    line = f.readline()
f.close()
percent = totalPercent/float(count)
time = totalTime/float(count)
fw = open('bmc_data/record.txt', 'a')
string = '{:.2f}'.format(percent)
fw.write('Average Percentage Fit is: ' + string + '% \n')
string = '{:.2f}'.format(time)
fw.write('Average Time Taken is: ' + string + '\n')
fw.close()