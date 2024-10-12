import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
 class Car{

        private String carId;

        private String brand;

        private String model;

       private double basePricePerDay;

       private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay, boolean isAvailable){
        this.carId = carId;
        this.brand = brand; // 'this'-assign the value of the carId parameter to the carId instance variable of this
        this.model = model;            //specific object
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    */
 class Car {
    private String id;
    private String make;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String id, String make, String model, double basePricePerDay) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    // Getters and setters, if needed




    public String getCarId(){  // 'get' (accessor methods) are used to retrieve the values of private
                                // instance variables within a class
        return id;
    }
    public String getBrand() {

        return make;
    }
    public String getModel(){

        return model;
    }
    public double calculatePrice(int rentalDays){

        return basePricePerDay * rentalDays;
    }
    public boolean isAvailable(){

        return isAvailable;
    }
    public void rent(){

        isAvailable = false;
    }
    public void returnCar() {

        isAvailable = true;
    }
 }

 class Customer {

    private String customerId;

    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {

        return customerId;
    }

    public String getName(){

        return name;
    }
 }

 class Rental{

    private Car car;         //Car -data type of the variable

    private Customer customer;

    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;  //this.car: Refers to the instance variable car of the current object (the object being created)
        this.customer = customer; //car: Refers to the parameter passed to the constructor.
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getcustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }
 }

 class CarRentalSystem {

     private List<Car> cars;   //These are private instance variables that store lists of Car, Customer, and Rental objects, respectively

     private List<Customer> customers;

     private List<Rental> rentals;

     public CarRentalSystem(){
         cars = new ArrayList<>();
         customers = new ArrayList<>();
         rentals = new ArrayList<>();
     }

     public void addCar(Car car){

         cars.add(car);
     }

     public void addCustomer(Customer customer) {

         customers.add(customer);
     }

     public void rentCar(Car car, Customer customer, int days){
         if(car.isAvailable()){
             car.rent();
             rentals.add(new Rental(car, customer, days));
         }
         else{
             System.out.println("Car is not available");
         }
     }

     public void returnCar (Car car){
            car.returnCar();
            Rental rentalToRemove = null;
            for(Rental rental : rentals){
                if(rental.getCar() == car){
                    rentalToRemove = rental;
                    break;
                }
            }
            if(rentalToRemove != null){
                rentals.remove(rentalToRemove);
                System.out.println("Car returned successfully");
            }else{
                System.out.println("Car was not rented");
            }
     }


     public void menu(){
         Scanner sc = new Scanner(System.in);

         while(true){
             System.out.println("******CAR RENTAL SYSTEM******");
             System.out.println("1. Rent a Car");
             System.out.println("2. Return a Car");
             System.out.println("3. Exit");
             System.out.print("Enter your choice: ");

             int choice = sc.nextInt();
             sc.nextLine();                       //  consume newLine

             if(choice == 1){
                 System.out.println("\n** Rent a Car **\n");
                 System.out.println("Enter your name : ");
                 String customerName = sc.nextLine();

                 System.out.println("\nAvailable Cars:");
                 for (Car car : cars){
                     if(car.isAvailable()){
                         System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                     }
                 }

                 System.out.print("\nEnter the car ID you want to rent: ");
                 String carId = sc.nextLine();

                 System.out.print("Enter the number of days for rental: ");
                 int rentalDays = sc.nextInt();
                 sc.nextLine();


                 Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                 addCustomer(newCustomer);

                 Car selectedCar = null;
                 for(Car car : cars){
                     if(car.getCarId().equals(carId) && car.isAvailable()){
                         selectedCar = car;
                         break;
                     }
                 }

                 if(selectedCar != null){
                     double totalPrice = selectedCar.calculatePrice(rentalDays);
                     System.out.println("\n== Rental Information ==\n");
                     System.out.println("Customer ID: " + newCustomer.getCustomerId());
                     System.out.println("Customer Name: " + newCustomer.getName());
                     System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                     System.out.println("Rental Days: " + rentalDays);
                     System.out.printf("Total Price: $%.2f%n", totalPrice);

                     System.out.print("\nConfirm rental (Y/N):");
                     String confirm = sc.nextLine();

                     if(confirm.equalsIgnoreCase("Y")){
                         rentCar(selectedCar, newCustomer, rentalDays);
                         System.out.println("\nCar rented successfully");

                     } else {
                         System.out.println("\nRental canceled");
                     }
                 }else{
                     System.out.println("\nInvalid car selection or car not available for rent");
                 }
                 
             } else if (choice == 2){
                 System.out.println("\n== Return a Car ==\n");
                 System.out.print("Enter the car ID you want to return:");
                 String carId = sc.nextLine();

                 Car carToReturn = null;
                 for(Car car : cars){
                     if(car.getCarId().equals(carId) && !car.isAvailable()){
                         carToReturn = car;
                         break;
                     }
                 }
                 if(carToReturn != null){
                     Customer customer = null;
                     for (Rental rental : rentals){
                         if(rental.getCar() == carToReturn){
                             customer = rental.getcustomer();
                             break;
                         }
                     }

                     if(customer != null){
                         returnCar(carToReturn);
                         System.out.println("Car returned successfully by " + customer.getName());
                     } else{
                         System.out.println("Car was not rented or rental information is missing");
                     }
                 }else {
                     System.out.println("Invalid car ID or car in not rented ");
                 }
             }else if (choice == 3){
                 break;
             }else{
                 System.out.println("Invalid choice. Please enter a valid option");
             }
         }

         System.out.println("\n Thank you for using the Car rental system");
     }

 }
/*
public class Main{

    public static void main(String[] args){
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car(carId : "C001", brand: "Rolls-Royce", model: "Phantom", basePricePerDay: 60.0);
        Car car2 = new Car(carId: "C002", brand: "Hindustan",   model: "Contessa",basePricePerDay: 70.0);
        Car car3 = new Car(carId: "C003", brand: "Tata"     ,   model: "Nano"   , basePricePerDay: 150.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}*/

