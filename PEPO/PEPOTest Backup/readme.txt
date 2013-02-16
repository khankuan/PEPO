Command 1: "pepotest system;test1-orientationcamp"
- A prompt to clear current Data will appear.
- This commands runs a single set of test. The test folder must be located in the /PEPOTest folder.
- Note that no spaces should be used for these test folders' names; 
- Each test folder contains:

1) testcommands.txt: These commands are exactly the same as how TextUI is used, and includes a command "ENDOFTEST" at the end of the file. All commands will be run during the test.

2) expectedoutput.txt: This text file contains all the expected outputs of TextUI, either "Success!" or "Fail".

3) ExpectedData: This is a folder containing a set of XML files that are the resulting saved data of the testcommands.txt.

4) initialData: This is a folder containing a set of XML files that are the initial saved data state before any commands in testcommands.txt is run.

- Original data are repalced with the initial data folder. Missing files in initial data folder will only be created when test starts (empty xml).
- The test begins by taking in inputs from testcommands.txt. The outputs are redirected to pepotest_output.txt.
- It will then do a comparsion between the pepotest_output.txt and expectedoutput.txt. A success message will be outputted if both files are the same. Otherwise, a fail message will be outputted together with the line of which the data are different.
- It will then do a comparsion between the Data in PEPO folder and ExpectedData in the test folder. A success message will be outputted if both files are the same. Otherwise, a fail message will be outputted together with the line of which the data are different.
- Finally, the result of the entire test is outputted.



Command 2: "pepotest unit;test1-orientationcamp"
- same as the above test, but Data xml files are not compared



Command 3: batchcommands filename.txt"
- Run a set of commands from a file
- Runs multiple commands and output the results in order.
- For running multiple test, do remember to add in '1' to allow replacement of data:
pepotest unit;[test_name]
1
pepotest system;[test_name]
1
