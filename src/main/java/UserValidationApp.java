import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserValidationApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Date of Birth (yyyy-MM-dd): ");
        String dobString = scanner.nextLine();

        ValidationResult result = validateInput(username, email, password, dobString);
        if (result.isValid()) {
            System.out.println("Validation passed.");
            String token = TokenGenerator.generateToken(username);
            TokenVerifier.verifyToken(token);
        } else {
            System.out.println("Validation failed for the following fields:");
            for (String message : result.getMessages()) {
                System.out.println(message);
            }
        }
    }

    public static ValidationResult validateInput(String username, String email, String password, String dobString) {
        ValidationResult result = new ValidationResult();

        if (username.length() < 4) {
            result.addMessage("Username: not empty and should be at least 4 characters.");
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            result.addMessage("Email: not empty and should be a valid email address.");
        }

        if (password.isEmpty() || !isStrongPassword(password)) {
            result.addMessage("Password: not empty, should contain 1 upper case, 1 special character, 1 number, and be at least 8 characters long.");
        }

        if (dobString.isEmpty() || !isValidDateOfBirth(dobString)) {
            result.addMessage("Date of Birth: not empty and should be 16 years or greater.");
        }

        return result;
    }

    public static boolean isValidEmail(String email) {
        // Use a simple regex pattern to check for a valid email format.
        String regex = "^(.+)@(.+)$";
        return Pattern.matches(regex, email);
    }

    public static boolean isStrongPassword(String password) {
        // Check if the password contains 1 upper case, 1 special character, 1 number, and is at least 8 characters long.
        return password.matches("^(?=.*[A-Z])(?=.*[!@#\\$%^&*])(?=.*[0-9]).{8,}$");
    }

    public static boolean isValidDateOfBirth(String dobString) {
        // Check if the date of birth is valid and the person is at least 16 years old.
        try {
            LocalDate dob = LocalDate.parse(dobString);
            LocalDate currentDate = LocalDate.now();
            LocalDate sixteenYearsAgo = currentDate.minusYears(16);

            return !dob.isAfter(sixteenYearsAgo);
        } catch (Exception e) {
            return false;
        }
    }
}

class ValidationResult {
    private boolean valid = true;
    private final StringBuilder messages = new StringBuilder();

    public void addMessage(String message) {
        valid = false;
        messages.append(message).append("\n");
    }

    public boolean isValid() {
        return valid;
    }

    public String[] getMessages() {
        return messages.toString().split("\n");
    }
}

