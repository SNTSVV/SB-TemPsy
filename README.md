___
# *SBTemPsy*

*SBTemPsy* (**S**ignal-**B**ased **Tem**poral **P**roperty made ea**sy**) is an offline trace-checking tool for checking signal-based temporal properties over execution traces.
Our work covers two contributions:
  - `SBTemPsy-DSL`: a Domain Specific Language (DSL) for specification of signal-based temporal properties.
  - `SBTemPsy-Check`: an automated offline procedure for checking `SBTemPsy-DSL` properties on execution traces.

*SBTemPsy-Check* takes as input a CPS requirement and a simulation trace. It returns a boolean verdict. The latter evaluates to `true` if the property holds on that execution trace. Otherwise, it returns `false`.

##### Publications
- <a href="https://orbilu.uni.lu/handle/10993/44159">Trace-Checking Signal-based Temporal Properties: A Model-Driven Approach</a> <br/>
<i>Chaima Boufaied, Claudio Menghi, Domenico Bianculli, Lionel Briand, Yago Isasi Parache<br/>
Automated Software Engineering (ASE) 2020 <br/>
</i>

##### Package Content
- `ASE2020Results`: Contains the results of our ASE 2020 paper.
- `lu.svv.offline`: Contains the source code of *SBTemPsy-Check*.
- `releases/tag/v0.0.1`: Contains *SBTemPsy-Check*.
- `lu.svv.offline/lib/sb-tempsy-check.ocl`: Contains the optimized encoding in OCL.
- `lu.svv.offline/lib/init-sb-tempsy-check.ocl`: Contains the one2one encoding in OCL.

---
## ASE 2020 Results
To replicate the results of the ASE  paper (RQ2)
- open the `ASE2020Results` folder
- run the Matlab scripts `comparison.m` and `sbtempsyResults.m`
- the results will be displayed on the screen

---
## Running SBTemPsy-Check

#### Requirements
- Java 1.7+
- Eclipse OCL 6.0.1+

####  SBTemPsy-Check Usage
1. Create the `property` of interest.
    - The property of interest should be stored in an `.xmi` file.
    - The `.xmi` file should be compliant with the grammar of the [sbtempsy.ecore](lu.svv.offline/models/sbtempsy.ecore)  (lu.svv.offline/models/sbtempsy.ecore)  model
    - An example of `.xmi` property is the property [pb1.xmi](example/pb1.xmi) that can be found in `example/pb1.xmi`
2. Prepare your `trace`
    - The trace under analysis should be stored in an `.csv` getFile
    - Each row of the `.csv` file contains a record. The record is made by its timestamp, and a sequence of comma separated values containing the name of the signal and its value
    - Each row should contain for each signal its value
    - An example of `.csv` file containing a trace is the file [b1.csv](example/b1.csv) that can be found in `example/b1.csv`
3. `Run` SBTemPsy-Check
    - Download the last version of [SBTemPsy-Check](https://github.com/SNTSVV/SB-TemPsy/releases) from [https://github.com/SNTSVV/SB-TemPsy/releases](https://github.com/SNTSVV/SB-TemPsy/releases)
    - run `java -jar SBTemPsy.jar propertyFile traceFile resultFile`
    - for example, run `java -jar SBTemPsy.jar example/pb1.xmi example/b1.csv example/results.txt` to check whether the property specified in the file `pb1.xmi ` holds on the trace `b1.csv`. The results are displayed on the console and also written in the file `results.txt`

####  SBTemPsy-Check for developers
Please visit [https://github.com/SNTSVV/SB-TemPsy/wiki](https://github.com/SNTSVV/SB-TemPsy/wiki)



 Copyright by University of Luxembourg 2019-2020. <br/>
   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg.<br/> 
   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg.<br/> 
   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. <br/>
   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 
