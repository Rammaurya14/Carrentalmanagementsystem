package oops;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;


 class Car {

     private String carId;
     private String brand;
     private String model;
     private double basePricePerDay; // Changed to double
     private boolean isAvailable;

     // Constructor
     public Car(String carId, String brand, String model, double basePricePerDay) { // Changed parameter type
         this.carId = carId;
         this.brand = brand;
         this.model = model;
         this.basePricePerDay = basePricePerDay; // Corrected variable name
         this.isAvailable = true;
     }

     // Getters
     public String getCarId() {
         return carId;
     }

     public String getBrand() {
         return brand;
     }

     public String getModel() {
         return model;
     }

     // Calculate rental price
     public double calculatePrice(int rentalDays) {
         return basePricePerDay * rentalDays; // Corrected to use double
     }

     // Availability methods
     public boolean isAvailable() {
         return isAvailable;
     }

     public void rent() {
         isAvailable = false;
     }

     public void returnCar() {
         isAvailable = true;
     }
 }

 class Customer {

     private String name;

     private String id;

     private int phoneNumber;


     public Customer(String name, String id, int phoneNumber) {

         this.name = name;

         this.id = id;

         this.phoneNumber = phoneNumber;


     }

     public String getName() {

         return name;


     }

     public String getId() {
         return id;
     }

     public int getPhoneNumber() {

         return phoneNumber;
     }


 }


 class Rental {

     private Car car;

     private Customer customer;
     private int days;

     public Rental(Car car, Customer customer, int days) {


         this.car = car;
         this.customer = customer;
         this.days = days;


     }


     public Car getCar() {

         return car;

     }

     public Customer getCustomer() {

         return customer;
     }

     public int getDays() {

         return days;
     }


 }


 class CarRentalSystem {

     private List<Car> cars;
     private List<Customer> customers;
     private List<Rental> rentals; // Ensure Rental class is defined
     private Scanner scanner;

     public CarRentalSystem() {
         cars = new ArrayList<>();
         customers = new ArrayList<>();
         rentals = new ArrayList<>(); // Corrected to ArrayList
         scanner = new Scanner(System.in);
     }

     public void addCar(Car car) {
         cars.add(car);
     }

     public void addCustomer(Customer customer) {
         customers.add(customer);
     }

     public void rentCar(Car car, Customer customer, int days) {
         if (car.isAvailable()) {
             car.rent();
             rentals.add(new Rental(car, customer, days)); // Ensure Rental constructor is defined
             System.out.println("Car rented successfully to " + customer.getName() + " for " + days + " days. Total price: $" + car.calculatePrice(days));
         } else {
             System.out.println("Car is not available for rent.");
         }
     }

     public void returnCar(Car car) {
         car.returnCar();
         Rental rentalToRemove = null;

         for (Rental rental : rentals) {
             if (rental.getCar().equals(car)) { // Use .equals() for object comparison
                 rentalToRemove = rental;
                 break;
             }
         }

         if (rentalToRemove != null) {
             rentals.remove(rentalToRemove); // Remove the rental from the list
             System.out.println("Car returned successfully.");
         } else {
             System.out.println("Car was not rented.");
         }
     }


     public void menu() {


         while (true) {

             System.out.println("\n===== Car Rental System =====");

             System.out.println("1. Rent a car");
             System.out.println("2. Return a car");
             System.out.println("3. Exit");
             System.out.print("Enter your choice: ");


             int choice = scanner.nextInt();
             scanner.nextLine(); // Consume newline


             switch (choice) {
                 case 1:
                     rentCarMenu();
                     break;
                 case 2:
                     returnCarMenu();
                     break;
                 case 3:
                     System.out.println("Thank you for using the Car Rental System!");
                     scanner.close();
                     return;
                 default:
                     System.out.println("Invalid choice. Please try again.");
             }
         }

     }

     private void rentCarMenu() {
         System.out.println("\n== Rent a Car ==\n");
         if (cars.isEmpty()) {
             System.out.println("No cars available in the system.");
             return;
         }

         System.out.println("Available cars:");
         for (Car car : cars) {
             if (car.isAvailable()) {
                 System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
             }
         }

         System.out.print("Enter the Car ID you want to rent: ");
         String carIdToRent = scanner.nextLine();

         Car selectedCar = null;
         for (Car car : cars) {
             if (car.getCarId().equalsIgnoreCase(carIdToRent) && car.isAvailable()) {
                 selectedCar = car;
                 break;
             }
         }

         if (selectedCar == null) {
             System.out.println("Invalid car ID or the car is not available.");
             return;
         }

         System.out.print("Enter your name: ");
         String customerName = scanner.nextLine();

         System.out.print("Enter your ID: ");
         String customerId = scanner.nextLine();

         System.out.print("Enter your phone number: ");
         int customerPhoneNumber = scanner.nextInt();
         scanner.nextLine(); // Consume newline

         Customer customer = new Customer(customerName, customerId, customerPhoneNumber);
         addCustomer(customer);

         System.out.print("Enter the number of days you want to rent the car: ");
         int rentalDays = scanner.nextInt();
         scanner.nextLine(); // Consume newline

         rentCar(selectedCar, customer, rentalDays);
     }

     private void returnCarMenu() {
         System.out.println("\n== Return a Car ==\n");
         if (rentals.isEmpty()) {
             System.out.println("No cars are currently rented.");
             return;
         }

         System.out.println("Rented cars:");
         for (Rental rental : rentals) {
             System.out.println("Car ID: " + rental.getCar().getCarId() + ", Rented by: " + rental.getCustomer().getName());
         }

         System.out.print("Enter the Car ID you want to return: ");
         String carIdToReturn = scanner.nextLine();

         Car carToReturn = null;
         for (Car car : cars) {
             if (car.getCarId().equalsIgnoreCase(carIdToReturn) && !car.isAvailable()) {
                 carToReturn = car;
                 break;
             }
         }

         if (carToReturn == null) {
             System.out.println("Invalid car ID or the car was not rented.");
             return;
         }

         returnCar(carToReturn);
     }
 }


 public class carmanagement {

     public static void main(String[] args) {

         CarRentalSystem rentalSystem = new CarRentalSystem();
         Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
         Car car2 = new Car("B002", "BMW", "X5", 100.0);
         Car car3 = new Car("H003", "Honda", "Civic", 50.0);

         rentalSystem.addCar(car1);
         rentalSystem.addCar(car2);
         rentalSystem.addCar(car3);

         rentalSystem.menu();

     }

 }