package exceptions;

public class ExceptionIsEmpty extends Exception {
    public ExceptionIsEmpty(String msg) {
        super(msg);  
    }
    public ExceptionIsEmpty() {
        super();
    }
}
