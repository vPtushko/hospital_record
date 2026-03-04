import java.util.Scanner;

public class LoginManager {
    private String username;
    private String password;
    private int maxAttempts;

    public LoginManager(String username, String password, int maxAttempts) {
        this.username = username;
        this.password = password;
        this.maxAttempts = maxAttempts;
    }

    // Method to perform login
    public boolean login() {
        Scanner sc = new Scanner(System.in);
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("Username: ");
            String userInput = sc.nextLine();
            System.out.print("Password: ");
            String passInput = sc.nextLine();

            if (userInput.equals(username) && passInput.equals(password)) {
                System.out.println("Login successful!\n");
                return true;
            } else {
                System.out.println("Invalid username or password.\n");
                attempts++;
            }
        }

        return false; // failed max attempts
    }
}
