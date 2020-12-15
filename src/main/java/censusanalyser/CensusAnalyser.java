package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCodeCSV> stateCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.RUN_TIME_EXCEPTION);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException{
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return stateCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM);
        }catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.RUN_TIME_EXCEPTION);
        }catch (CSVBuilderException e){
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numofEntries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numofEntries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census->census.state);
        this.sortCensusbyState(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException{
        if(stateCSVList == null || stateCSVList.size() == 0){
            throw new CensusAnalyserException("No State Code Data", CensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
        }
        Comparator<IndiaStateCodeCSV> censusComparator = Comparator.comparing(census->census.code);
        this.sortCensusbyStateCode(censusComparator);
        String sortedStateCodeCensusJson = new Gson().toJson(stateCSVList);
        return sortedStateCodeCensusJson;
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException{
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census->census.population);
        this.sortCensusbyPopulation(censusComparator);
        String sortedPopulationCensusJson = new Gson().toJson(censusCSVList);
        return sortedPopulationCensusJson;
    }

    public String getPopulationdensitySortedCensusData() throws CensusAnalyserException{
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census->census.densityPerSqKm);
        this.sortCensusbyPopulationDensity(censusComparator);
        String sortedPopulationDensityCensusJson = new Gson().toJson(censusCSVList);
        return sortedPopulationDensityCensusJson;
    }

    public String getAreaSortedCensusData() throws CensusAnalyserException{
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census->census.areaInSqKm);
        this.sortCensusbyArea(censusComparator);
        String sortedAreaCensusJson = new Gson().toJson(censusCSVList);
        return sortedAreaCensusJson;
    }

    private void sortCensusbyState(Comparator<IndiaCensusCSV> censusComparator) {
        for(int i=0;i<censusCSVList.size()-1;i++){
            for(int j=0; j<censusCSVList.size()-1;j++){
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void sortCensusbyStateCode(Comparator<IndiaStateCodeCSV> indiaStateCodeComparator){
        for(int i=0;i<stateCSVList.size()-1;i++){
            for(int j=0; j<stateCSVList.size()-1;j++){
                IndiaStateCodeCSV census1 = stateCSVList.get(j);
                IndiaStateCodeCSV census2 = stateCSVList.get(j+1);
                if(indiaStateCodeComparator.compare(census1,census2)>0){
                    stateCSVList.set(j, census2);
                    stateCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void sortCensusbyPopulation(Comparator<IndiaCensusCSV> censusComparator) {
        for(int i=0;i<censusCSVList.size()-1;i++){
            for(int j=0; j<censusCSVList.size()-1;j++){
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2)<0){
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void sortCensusbyPopulationDensity(Comparator<IndiaCensusCSV> censusComparator) {
        for(int i=0;i<censusCSVList.size()-1;i++){
            for(int j=0; j<censusCSVList.size()-1;j++){
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2)<0){
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void sortCensusbyArea(Comparator<IndiaCensusCSV> censusComparator) {
        for(int i=0;i<censusCSVList.size()-1;i++){
            for(int j=0; j<censusCSVList.size()-1;j++){
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2)<0){
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }
}
