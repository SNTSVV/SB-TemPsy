% Copyright by University of Luxembourg 2019-2020. 
%   Developed by Chaima Boufaied, chaima.boufaied@uni.lu University of Luxembourg. 
%   Developed by Claudio Menghi, claudio.menghi@uni.lu University of Luxembourg. 
%   Developed by Domenico Bianculli, domenico.bianculli@uni.lu University of Luxembourg. 
%   Developed by Lionel Briand, lionel.briand@uni.lu University of Luxembourg. 

clear
close all
%% Loads the traces to not be considered

%% Pre-processing
disp('***************');
disp('Pre-processing');
disp('***************');
preprocessingData = readtable(strcat('preprocessing.csv'),'ReadVariableNames',false);
preprocessingtime=str2double(preprocessingData{2:size(preprocessingData,1),1});
disp(strcat('Time  ',char(9),'  Max: ',num2str(max(preprocessingtime)),char(9),'  Min:',num2str(min(preprocessingtime)),char(9),'  Avg:',num2str(mean(preprocessingtime))));

%% SBTempsy 
disp('***************');
disp('SBTempsy');
disp('***************');
 for i=1:1:10
     newtable = readtable(strcat('sbtempsy/run_',num2str(i),'.csv'),'ReadVariableNames',false);
     if i~=1
        table=[table;newtable];
     else
        table=newtable;
     end
 end
 
 
idx = strfind(table{:,3},'-');
idx = find(not(cellfun('isempty', idx)));
nonfinished = length(idx);

disp(['Successfull Runs (%)' char(9) num2str((size(table,1)-nonfinished)/size(table,1)*100) char(9) char(9) 'Number:' char(9) num2str(size(table,1)-nonfinished) '/' num2str(size(table,1))]);
disp(['Unsuccessfull Runs (%)' char(9) num2str((nonfinished)/size(table,1)*100) char(9) char(9) 'Number:' char(9) num2str(nonfinished) '/' num2str(size(table,1))]);

tempsyAllTimes=table{1:size(table,1),4};
tempsyVal=table{1:size(table,1),3};

tempsyTime=[];
for i=1:1:size(tempsyAllTimes,1)
    if tempsyVal{i} ~='-'
        
        tempsyTime=[tempsyTime; tempsyAllTimes(i)];
    end
end

disp(['Time Successfull Runs' char(9) 'Max: ' char(9) num2str(max(tempsyTime)) char(9) char(9)...
    'Min: ' char(9) num2str(min(tempsyTime)) char(9) char(9)...
    'Avg: ' char(9) num2str(mean(tempsyTime))])




% checking how many complex properties are checkable

spec=table{1:size(table,1),1};
counter=0;
totaldifficult=0;

keySet={'p23','p25','p31','p32','p33','p34','p35','p36',...
    'p37','p38','p39','p40','p41','p42','p43',...
    'p44','p45','p46','p47','p48','p49','p50',...
    'p51', 'np22','p31','np23','np24','np25','np26','np27','np39','np40','np41'};
for i=1:1:size(tempsyAllTimes,1)
    
    if any(strcmp(keySet,spec{i}))
        if tempsyVal{i} ~='-'
            counter=counter+1;
        end
        totaldifficult=totaldifficult+1;
    end

end
disp(['Complex properties %' char(9) num2str(counter/totaldifficult*100) char(9) char(9) 'Total Runs' char(9) num2str(counter) '/' num2str(totaldifficult)])


