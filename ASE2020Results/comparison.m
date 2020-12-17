% Copyright by University of Luxembourg 2019-2020. 
%   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
%   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
%   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
%   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 

clear
close all

%13


   
   
tb = readtable(strcat('breach/run_1.csv'),'ReadVariableNames',false);
traces=tb{1:size(tb,1),2};

   
%% SBTempsy 
 for i=1:1:10
     newtable = readtable(strcat('sbtempsy/run_',num2str(i),'.csv'),'ReadVariableNames',false);
     if i~=1
        table=[table;newtable];
     else
        table=newtable;
     end
 end
 
spec=table{1:size(table,1),1};
tempsyTraces=table{1:size(table,1),2};
tempsyVal=table{1:size(table,1),3};
tempsyAllTimes=table{1:size(table,1),4};

tempsyTime=[];
countends=0;
countnonends=0;
for i=1:1:size(tempsyAllTimes,1)
    specification=spec{i};
        if any(strcmp(traces,tempsyTraces{i}))
            if tempsyVal{i} ~='-'
                tempsyTime=[tempsyTime; tempsyAllTimes(i)];
                countends=countends+1;
            else
                %  disp(spec{i})
                countnonends=countnonends+1;
            end
        end

end

disp('SBTempsy');
disp(['Nruns: ' char(9) num2str(countends+countnonends) char(9)  char(9) 'Num Successfull' char(9) num2str(countends) '/' num2str(countends+countnonends) char(9) char(9) '% successfull' char(9)  num2str(countends/(countends+countnonends)*100)]);
disp(['% unsuccessfull' char(9) num2str(countnonends/(countends+countnonends)*100)]);
disp(['Time' char(9) ...
    'Max' char(9) num2str(max(tempsyTime)) char(9) char(9) ...
    'Min:' char(9) num2str(min(tempsyTime)) char(9) char(9) ...
    'Avg' char(9) num2str(mean(tempsyTime))]);

%% breach

 for i=1:1:10
     newtable = readtable(strcat('breach/run_',num2str(i),'.csv'),'ReadVariableNames',false);
     if i~=1
        breachtable=[breachtable;newtable];
     else
        breachtable=newtable;
     end
 end
breachAllTimes=breachtable{1:size(breachtable,1),4};
breachVal=breachtable{1:size(breachtable,1),3};
breachTraces=breachtable{1:size(breachtable,1),2};
spec=breachtable{1:size(breachtable,1),1};

breachTime=[];
countends=0;
countnonends=0;
for i=1:1:size(breachAllTimes,1)
        if breachVal{i} ~='-'
            breachTime=[breachTime; breachAllTimes(i)];
            countends=countends+1;
        else
            
            countnonends=countnonends+1;
        end
end

disp('Breach');
disp(['Nruns: ' char(9) num2str(countends+countnonends) char(9)  char(9) 'Num Successfull' char(9) num2str(countends) '/' num2str(countends+countnonends) char(9) char(9) '% successfull' char(9)  num2str(countends/(countends+countnonends)*100)]); 
disp(['Time' char(9) ...
    'Max' char(9) num2str(max(breachTime)) char(9) char(9) ...
    'Min:' char(9) num2str(min(breachTime)) char(9) char(9) ...
    'Avg' char(9) num2str(mean(breachTime))]);



