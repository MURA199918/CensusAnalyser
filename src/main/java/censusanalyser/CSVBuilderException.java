package censusanalyser;

public class CSVBuilderException extends Exception {
    public CSVBuilderException(String message, CensusAnalyserException.ExceptionType name) {
    }

    enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, RUN_TIME_EXCEPTION
    }

    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
