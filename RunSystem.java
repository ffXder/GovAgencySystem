import java.util.Scanner;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.BufferedWriter;

interface Forms {
    public void fillOut();

    public void retrieveInfo(int id);
}

class PersonalInfo {
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffix;
    private String gender;
    private String dateOfBirth;
    private String birthCountry;
    private String birthProvince;
    private String birthPlace;
    private String maritalStatus;
    private String bloodType;

    public PersonalInfo() {
    }

    public PersonalInfo(String firstName, String middleName, String lastName, String gender,
            String dateOfBirth, String birthCountry, String birthProvince, String place, String status, String type) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.birthCountry = birthCountry;
        this.birthProvince = birthProvince;
        this.birthPlace = place;
        this.maritalStatus = status;
        this.bloodType = type;
    }

    // constructor overloading adding additional data type
    public PersonalInfo(String firstName, String middleName, String lastName, String suffix, String gender,
            String dateOfBirth, String birthCountry, String birthProvince, String place, String status, String type) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = suffix; // additional data type
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.birthCountry = birthCountry;
        this.birthProvince = birthProvince;
        this.birthPlace = place;
        this.maritalStatus = status;
        this.bloodType = type;
    }

    public void displayInfo() {
        System.out.println("---------------------------------------");
        System.out.println("Personal Information ");
        System.out.println("First Name (Pangalan): " + firstName
                + "\nMiddle Name (Gitnang Pangalan): " + middleName
                + "\nLastName (Apelyido): " + lastName);
        System.out.println("Sex (Kasarian): " + gender);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Birth Country (Lugar ng Kapanganakan): " + birthCountry);
        System.out.println("Birth Province (Probinsiya ng Kapanganakan): " + birthProvince);
        System.out.println("City/Municipality (Lungsod Ng Kapanganakan): " + birthPlace);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Blood Type (Uri ng Dugo): " + bloodType);
        System.out.println("--------------------------------------");
    }
}

class Address extends PersonalInfo {
    private String type;
    private String address, barangay, place, province, country, zipCode, mobileNumber, email;

    public void setAddress(String address, String barangay, String place, String province, String country,
            String code, String number, String email) {
        this.address = address;
        this.barangay = barangay;
        this.place = place;
        this.province = province;
        this.country = country;
        this.zipCode = code;
        this.mobileNumber = number;
        this.email = email;
    }

    public void displayInfo() {
        System.out.println("---------------------------------------");
        System.out.println("Address");
        System.out.println("Address (Tirahan): " + address);
        System.out.println("Barangay: " + barangay);
        System.out.println("City/Municipality *Lungsod/Bayan): " + place);
        System.out.println("Province (Probinsya): " + province);
        System.out.println("County (Bansa): " + country);
        System.out.println("ZIP Code: " + zipCode);
        System.out.println("Mobile no: " + mobileNumber);
        System.out.println("Email Address: " + email);
        System.out.println("---------------------------------------");
    }

}

class NationalIDSystem implements Forms {
    private Map<Integer, PersonalInfo> database = new HashMap<>();
    private Scanner read = new Scanner(System.in);
    private Random generate = new Random();
    boolean isConfirmed = false;

    // for prompt
    private String getString(String prompt) {
        System.out.print(prompt);
        return read.nextLine();
    }

    // this method will validate if the user input a valid gender
    private String getValidatedGender() {
        String gender;
        while (true) {
            gender = getString("Enter Sex (Kasarian): ");
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("M")
                    || gender.equalsIgnoreCase("F")) {
                return gender;
            } else {
                System.out.println("Invalid gender. Please enter Male or Female.");
            }
        }

    }

    // this method will validate if the user input a valid date
    private String getValidatedBirthDate() {
        while (true) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String birthDate = getString("Enter date of birth (YYYY/MM/DD): ");
                LocalDate date = LocalDate.parse(birthDate, format);

                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Date cannot be in the future. Please try again.");
                    continue;
                }

                Period period = Period.between(date, LocalDate.now());
                int age = period.getYears();

                if (age < 0) {
                    System.out.println("Please enter a valid date.");
                    continue;
                }

                System.out.println("Age: " + age);
                return birthDate; // Return the valid date
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY/MM/DD.");
            }
        }
    }

    // this method will validate the blood type inputted by users
    private String getValidatedBloodType() {
        while (true) {
            String bloodType = getString("Enter Blood Type: ").toUpperCase();
            if (bloodType.matches("(A|B|O|AB)[+-]")) {
                return bloodType;
            } else {
                System.out.println("Please enter a valid bloodtype.");
            }
        }

    }

    public void fillOut() {

        // Display
        System.out.println("Please fill out the information needed.");
        String firstName = getString("Enter First Name (Pangalan): ");
        String middleName = getString("Enter Middle Name (Gitnang Pangalan): ");
        String lastName = getString("Enter Last Name (Apelyido): ");

        // System.out.print("Does your name have suffix? (Y/N): ");
        // String choice = read.nextLine();

        String gender = getValidatedGender();

        String birthDate = getValidatedBirthDate();

        // Address
        String birthCountry = getString("Enter Birth Country: ");
        String birthProvince = getString("Enter Birth Province: ");
        String birthPlace = getString("Enter Birth City/Municipality: ");

        String maritalStatus = getString("Enter Marital Status: ");
        String bloodType = getValidatedBloodType();

        PersonalInfo personalInfo = new PersonalInfo(firstName, middleName, lastName, gender,
                birthDate,
                birthCountry,
                birthProvince, birthPlace, maritalStatus, bloodType);

        personalInfo.displayInfo();
        System.out.println("Press Y to confirm submission or Press N to cancel");
        String confirmation = read.nextLine();

        // adds confirmation before submitting data
        if (confirmation.equalsIgnoreCase("Y")) {
            int nationalID = generate.nextInt(100) + 50; // genarate id using random numbers
            database.put(nationalID, personalInfo);
            System.out.println("Successfully registered. Thank you for using our System."
                    + "\nYour ID number is " + nationalID);

        } else {
            System.out.println("Successfully cancalled. Thank you for using our system.");
        }

    }

    // this method will retrieve the information
    public void retrieveInfo(int id) {

        if (database.containsKey(id)) {
            PersonalInfo person = database.get(id);
            System.out.println("ID number: " + id);
            person.displayInfo();
        } else {
            System.out.println("The ID Number " + id + " is not found");
        }
    }

    public void findID() {
        try {
            System.out.print("Enter national ID: ");
            int ID = read.nextInt();
            retrieveInfo(ID);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class RunSystem {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        NationalIDSystem system = new NationalIDSystem();

        while (true) {
            System.out.println("=============================================");
            System.out.println(" Welcome to Philippine Identification System ");
            System.out.println("=============================================");
            System.out.println("1 - Register\n2 - Find ID\n3 - Exit");
            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1 -> system.fillOut();
                case 2 -> system.findID();
                case 3 -> {
                    System.out.println("Are you sure you want to quit? (Y/N)");
                    String confirm = input.nextLine().trim();

                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("Thank you for using our system! Exiting the Program.");
                        input.close();
                        System.exit(0);
                    }

                }
                default -> System.out.println("Invalid input");
            }
        }
    }
}