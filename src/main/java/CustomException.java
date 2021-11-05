public class CustomException extends RuntimeException {
    String message;
    String solution;
    public CustomException(String message, String solution)
    {
        this.message=message;
        this.solution=solution;
    }
}
