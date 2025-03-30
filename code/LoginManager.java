import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginManager {
    private static Map<String, User> users;

    public LoginManager() {
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getNric(), user);
    }

    public User authenticate(String nric, String password) {
        User user = users.get(nric);
        if (user != null && user.login(nric, password)) {
            return user;
        }
        return null;
    }

    public void loadTestData() {
        addUser(new Applicant("S1234567A", 30, "Single"));
        addUser(new HDBManager("T7654321B", 45, "Married"));
        addUser(new HDBOfficer("S2345678C", 35, "Married"));
    }

    public void uploadUserData(String filePath, List<User> userList) {
        try {
            User.uploadUserData(filePath, userList);
            for (User user : userList) {
                addUser(user);
            }
            System.out.println("User data uploaded successfully.");
        } catch (IOException e) {
            System.out.println("Error uploading user data: " + e.getMessage());
        }
    }

    public static User getUserByNric(String nric) {
        return users.get(nric);
    }
}
