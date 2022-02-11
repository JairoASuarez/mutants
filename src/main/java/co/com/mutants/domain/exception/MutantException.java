package co.com.mutants.domain.exception;

public class MutantException extends Exception{

    private String message;
    private String errorCode;

    public MutantException(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
