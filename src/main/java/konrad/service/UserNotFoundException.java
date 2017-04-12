package konrad.service;

public class UserNotFoundException extends RuntimeException {
    private final String username;

    public UserNotFoundException(String username) {
        super(String.format("User %s not found", username));
        this.username = username;
    }
}
