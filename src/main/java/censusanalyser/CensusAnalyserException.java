package censusanalyser;

public class CensusAnalyserException extends Exception {

    public CensusAnalyserException(String message, String name) {
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, STATE_FILE_PROBLEM, NO_CENSUS_DATA, RUN_TIME_EXCEPTION
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
