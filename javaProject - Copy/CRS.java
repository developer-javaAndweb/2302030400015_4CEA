import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.Scanner;
class Car {
    private String carId;

    private String brand;

    private String model;

    private double basePricePerDay;

    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay, boolean isAvailable)
    {
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basePricePerDay=basePricePerDay;
        this.isAvailable=true;
    }

    public String getCarId()
    {
        return carId;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getModel()
    {
        return model;
    }

    public double calculatePrice(int rentalDays)
    {
        return basePricePerDay*rentalDays;
    }


    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void rent()
    {
        isAvailable=false;
    }

    public  void returnCar()
    {
        isAvailable=true;
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

    public String getName() {
        return name;
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
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
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
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("car is not available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental  rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("car returned successfully.");
        } else {
            System.out.println("car was not returned.");
        }

    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== car Rental System =====");
            System.out.println("1. rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3.Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                System.out.println("\n == Rent a Car ==\n");
                System.out.println("Enter your name: ");
                String customerName = scanner.nextLine();  //customerName
                System.out.println("\nAvailable Cars: ");
                for (Car car : cars)
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " " + car.getBrand() + " " + car.getModel());
                        System.out.println("\nEnter the car id you want to rent:");
                        String carId = scanner.nextLine();
                        System.out.println("Enter the number of days for rental:");
                        int rentalDays = scanner.nextInt();
                        scanner.nextLine();
                        Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                        addCustomer(newCustomer);
                        Car selectedCar = null;
                        for (Car cars : cars) {
                            if (car.getCarId().equals(carId) && car.isAvailable()) {
                                selectedCar = car;
                                break;
                            }
                            if (selectedCar != null) {
                                double totalPrice = selectedCar.calculatePrice(rentalDays);
                                System.out.println("\n== Rental Information ==\n");
                                System.out.println("Customer id: " + newCustomer.getCustomerId());
                                System.out.println("Customer name: " + newCustomer.getName());
                                System.out.println("car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                                System.out.println("Rental days: " + rentalDays);
                                System.out.println("Total price:$%.2f%n " + totalPrice);
                                System.out.println("\nConfirm rental (Y/N): ");
                                String confirm = scanner.nextLine();

                                if (confirm.equalsIgnoreCase("y")) {
                                    rentCar(selectedCar, newCustomer, rentalDays);
                                    System.out.println("\ncar rented successfully.");
                                } else {
                                    System.out.println("\nRental canceled. ");
                                }
                            } else {
                                System.out.println("\nInvalid car selection or car not available for rent. ");
                            }
                        }
                    } else if (choice == 2) {
                        System.out.println("\n== return a car ==\n");
                        System.out.println("Enter the car id that you return: ");
                        String carid = scanner.nextLine();

                        Car carToReturn = null;
                        for (Car cars : cars) {
                            if (car.getCarId().equals(carid) && !car.isAvailable()) {
                                carToReturn = car;
                                break;
                            }
                            if (carToReturn != null) {
                                Customer customer = null;
                                for (Rental rental : rentals) {
                                    if (rental.getCar() == carToReturn) {
                                        customer = rental.getCustomer();
                                        break;
                                    }

//                                    if (customer != null) {
//                                        rentCar(carToReturn);
//                                        System.out.println("Car returned successfully by " + customer.getName());
//                                    } else {
//                                        System.out.println("car was not rented or rental information is missing, ");
//
//                                    }
//                                }else{
//                                    System.out.println("Invalid ar id or car is not returned ");
//                                }
                                }
                            }else if (choice == 3) {
                                break;
                            } else {
                                System.out.println("Invalid choice. Please enter a valid option.");
                            }
                        }
                        System.out.println("\nThank you for using the Car rental System.");
                    }

            }
        }
    }
}

                public class CRS {
                    public static void main(String[] args) {
                        CarRentalSystem rentalSystem = new CarRentalSystem();

                        Car car1 = new Car("C001", "Toyoto", "Carry", 60.00,true);
                        Car car2 = new Car("C003", "honda", "Accord", 70.0,true);
                        rentalSystem.addCar(car1);
                        rentalSystem.addCar(car2);
                        rentalSystem.menu();
                    }
                }
