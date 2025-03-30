import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class User {
    private String nric;
    private String password;
    private int age;
    private String maritalStatus;
    private EnquiryHandler enquiryHandler; // Add EnquiryHandler to User

    public User(String nric, int age, String maritalStatus) {
        this.nric = nric;
        this.password = "password"; // Default password
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.enquiryHandler = new EnquiryHandler(); // Initialize EnquiryHandler
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public boolean login(String nric, String password) {
        if (!isValidNric(nric)) {
            throw new IllegalArgumentException("Invalid NRIC format.");
        }
        return this.nric.equals(nric) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public static boolean isValidNric(String nric) {
        return nric.matches("[ST]\\d{7}[A-Z]");
    }

    public void updatePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
        } else {
            throw new IllegalArgumentException("Old password is incorrect.");
        }
    }

    public EnquiryHandler getEnquiryHandler() {
        return enquiryHandler;
    }

    public List<Enquiry> getEnquiries() {
        return enquiryHandler.getEnquiries();
    }

    public void submitEnquiry(String enquiryText) {
        enquiryHandler.submitEnquiry(enquiryText);
    }

    public void editEnquiry(int enquiryId, String newText) {
        enquiryHandler.editEnquiry(enquiryId, newText);
    }

    public void deleteEnquiry(int enquiryId) {
        enquiryHandler.deleteEnquiry(enquiryId);
    }

    public void replyToEnquiry(int enquiryId, String replyText, User repliedBy) {
        enquiryHandler.replyToEnquiry(enquiryId, replyText, repliedBy);
    }

    public abstract void viewProjects();

    public abstract String getRole(); // To differentiate roles (e.g., Applicant, HDBManager, HDBOfficer)

    public boolean isEligibleForProject(BTOProject project) {
        if (this instanceof Applicant) {
            Applicant applicant = (Applicant) this;
            return applicant.isEligibleForProject(project);
        }
        return false;
    }

    public static void uploadUserData(String filePath, List<User> userList) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nric = data[0];
                int age = Integer.parseInt(data[1]);
                String maritalStatus = data[2];
                String role = data[3];
                switch (role) {
                    case "Applicant":
                        userList.add(new Applicant(nric, age, maritalStatus));
                        break;
                    case "HDBManager":
                        userList.add(new HDBManager(nric, age, maritalStatus));
                        break;
                    case "HDBOfficer":
                        userList.add(new HDBOfficer(nric, age, maritalStatus));
                        break;
                }
            }
        }
    }

    public static User getUserByNric(String nric) {
        // Logic to retrieve user by NRIC (e.g., from a database or in-memory list)
        return LoginManager.getUserByNric(nric);
    }
}
