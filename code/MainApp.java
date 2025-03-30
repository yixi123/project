import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        loginManager.loadTestData();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the BTO Management System!");
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            User user = loginManager.authenticate(nric, password);
            if (user != null) {
                System.out.println("Login successful! Welcome, " + user.getNric());
                System.out.println("Role: " + user.getRole());

                // Example: Password change functionality
                System.out.print("Do you want to change your password? (yes/no): ");
                String changePassword = scanner.nextLine();
                if (changePassword.equalsIgnoreCase("yes")) {
                    System.out.print("Enter old password: ");
                    String oldPassword = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    user.updatePassword(oldPassword, newPassword);
                    System.out.println("Password updated successfully. Please log in again.");
                    return;
                }

                // Role-based navigation
                if (user instanceof Applicant) {
                    applicantMenu((Applicant) user, scanner);
                } else if (user instanceof HDBOfficer) {
                    hdbOfficerMenu((HDBOfficer) user, scanner);
                } else if (user instanceof HDBManager) {
                    hdbManagerMenu((HDBManager) user, scanner);
                }
            } else {
                System.out.println("Invalid NRIC or password.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void applicantMenu(Applicant applicant, Scanner scanner) {
        System.out.println("Applicant Menu:");
        int choice;
        do {
            System.out.println("1. View Projects");
            System.out.println("2. Apply for Project");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Application");
            System.out.println("5. Submit Enquiry");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1 -> applicant.viewProjects();
                case 2 -> {
                    System.out.print("Enter project name to apply: ");
                    String projectName = scanner.nextLine();
                    // Logic to fetch project by name and apply
                }
                case 3 -> applicant.viewApplicationStatus();
                case 4 -> applicant.withdrawApplication();
                case 5 -> {
                    System.out.print("Enter enquiry text: ");
                    String enquiryText = scanner.nextLine();
                    applicant.submitEnquiry(enquiryText);
                }
                case 6 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void hdbOfficerMenu(HDBOfficer officer, Scanner scanner) {
        System.out.println("HDB Officer Menu:");
        // Add menu options for HDB Officer
    }

    private static void hdbManagerMenu(HDBManager manager, Scanner scanner) {
        System.out.println("HDB Manager Menu:");
        // Add menu options for HDB Manager
    }
}
