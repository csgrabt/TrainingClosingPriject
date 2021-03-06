package covid.businesslogiclayer;

import covid.dataacceslayer.CitizenDao;

import java.time.LocalDate;


public class Citizen {
    private int id;
    private String fullName;
    private String zipCode;
    private int age;
    private String email;
    private String healthInsuranceNumber;
    private String vaccinationType;
    private int numberOfVaccination;
    private String status;
    private LocalDate lastVaccination;


    public Citizen() {

    }



    public Citizen(String fullName, String zipCode, int age, String email, String healthInsuranceNumber) {
        if (validatorName(fullName)) {
            throw new IllegalArgumentException("Name can not be null or empty!");
        }
        if (validatorAge(age)) {
            throw new IllegalArgumentException("Age is not correct!");
        }
        if (!validatorZipCode(zipCode)) {
            throw new IllegalArgumentException("Db does not contain the ZipCode, or digit is not 4!");
        }
        validatorHealthInsuranceNumber(healthInsuranceNumber);
        emailValidator(email);
        this.fullName = fullName;
        this.zipCode = zipCode;
        this.age = age;
        this.email = email;
        this.healthInsuranceNumber = healthInsuranceNumber;
    }


    public Citizen(String fullName, String zipCode, int age, String email, String healthInsuranceNumber, String vaccinationType, int numberOfVaccination) {
        this.fullName = fullName;
        this.zipCode = zipCode;
        this.age = age;
        this.email = email;
        this.healthInsuranceNumber = healthInsuranceNumber;
        this.vaccinationType = vaccinationType;
        this.numberOfVaccination = numberOfVaccination;
    }

    public Citizen(int id, String fullName, String zipCode, int age, String email, String healthInsuranceNumber) {
        this.id = id;
        this.fullName = fullName;
        this.zipCode = zipCode;
        this.age = age;
        this.email = email;
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

    public Citizen(int id, String vaccinationType, int numberOfVaccination, String status, LocalDate lastVaccination) {
        this.id = id;
        this.vaccinationType = vaccinationType;
        this.numberOfVaccination = numberOfVaccination;
        this.status = status;
        this.lastVaccination = lastVaccination;

    }

    public LocalDate getLastVaccination() {
        return lastVaccination;
    }

    public String getStatus() {
        return status;
    }
    public int getNumberOfVaccination() {
        return numberOfVaccination;
    }

    public int getId() {
        return id;
    }

    public String getVaccinationType() {
        return vaccinationType;
    }

    public String getFullName() {
        return fullName;
    }


    public String getZipCode() {
        return zipCode;
    }


    public int getAge() {
        return age;
    }


    public String getEmail() {
        return email;
    }


    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }


    public boolean validatorName(String name) {
        return name == null || name.isBlank();
    }

    public boolean validatorZipCode(String zipCode) {
        if (zipCode.length() != 4) {
            System.out.println("The zip code does not legal! (Must be 4 digits!)");
            return false;
        }
        try {
            new CitizenDao().findCityByZipCode(zipCode);
        } catch (IllegalArgumentException io) {
            return false;
        }
        return true;
    }

    public boolean validatorAge(int age) {
        return age <= 10 || age >= 150;
    }


    public void emailValidator(String string) {
        if (string.length() <= 2 || !(string.contains("@"))) {
            throw new IllegalArgumentException("Email address is not valid!");
        }
    }


    public void validatorHealthInsuranceNumber(String tajNumber) {


        if (tajNumber.length() != 9) {
            throw new IllegalArgumentException("The length of the insurance number is wrong!");
        }

        int b = tajNumber.length();

        int even = getEven(tajNumber, b);

        int odd = getOdd(tajNumber, b);

        boolean isValidTaj = (even + odd) % 10 == Integer.parseInt("" + tajNumber.charAt(b - 1));

        if (!isValidTaj) {
            throw new IllegalArgumentException("Health insurance number is wrong!");
        }
    }

    public int getOdd(String tajNumber, int b) {
        int odd = 0;
        for (int i = 0; i < b - 1; i = i + 2) {
            int d;
            try {
                d = Integer.parseInt("" + tajNumber.charAt(i));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("One of the Character is not number!");
            }
            odd += (d * 3);
        }
        return odd;
    }

    public int getEven(String tajNumber, int b) {
        int even = 0;
        for (int i = 1; i < b - 1; i = i + 2) {
            int d;
            try {
                d = Integer.parseInt("" + tajNumber.charAt(i));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("One of the Character is not number!");
            }
            even += (d * 7);
        }
        return even;
    }
}




