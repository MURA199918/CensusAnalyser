package censusanalyser;

public class CSVBuilderException extends Exception {
    public CSVBuilderException(String message, String name) {
    }

    enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, RUN_TIME_EXCEPTION
    }

    CensusAnalyserException.ExceptionType type;

    public CSVBuilderException(String message, CensusAnalyserException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, CensusAnalyserException.ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
