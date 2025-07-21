import java.util.*;

class EVehicle {
    int id;
    String name;
    String type; // e.g., Scooter, Bike, Car
    double pricePerHour;
    boolean isAvailable;

    public EVehicle(int id, String name, String type, double pricePerHour) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.isAvailable = true;
    }

    public void display() {
        System.out.printf("ID: %-4d Name: %-15s Type: %-10s Price/hr: ₹%.2f Available: %s\n",
                id, name, type, pricePerHour, isAvailable ? "Yes" : "No");
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<EVehicle> vehicles = new ArrayList<>();
    static int vehicleId = 1001;

    public static void main(String[] args) {
        if (!adminLogin()) {
            System.out.println("Access denied. Exiting.");
            return;
        }

        int choice;
        do {
            showMenu();
            choice = getInt();
            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> viewVehicles();
                case 3 -> rentVehicle();
                case 4 -> returnVehicle();
                case 5 -> System.out.println("Exiting system. Goodbye.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    static boolean adminLogin() {
        System.out.println("==== E-Vehicle Rental Admin Login ====");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        return user.equals("admin") && pass.equals("admin123");
    }

    static void showMenu() {
        System.out.println("\n==== E-Vehicle Rental System ====");
        System.out.println("1. Add E-Vehicle");
        System.out.println("2. View All Vehicles");
        System.out.println("3. Rent a Vehicle");
        System.out.println("4. Return a Vehicle");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    static void addVehicle() {
        sc.nextLine();
        System.out.print("Enter vehicle name: ");
        String name = sc.nextLine();
        System.out.print("Enter vehicle type (Scooter/Bike/Car): ");
        String type = sc.nextLine();
        System.out.print("Enter price per hour: ");
        double price = getDouble();

        EVehicle vehicle = new EVehicle(vehicleId++, name, type, price);
        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully.");
    }

    static void viewVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }

        System.out.println("\nAvailable Vehicles:");
        for (EVehicle v : vehicles) {
            v.display();
        }
    }

    static void rentVehicle() {
        viewVehicles();
        if (vehicles.isEmpty()) return;

        System.out.print("Enter vehicle ID to rent: ");
        int id = getInt();
        EVehicle vehicle = findVehicleById(id);

        if (vehicle == null || !vehicle.isAvailable) {
            System.out.println("Vehicle not found or not available.");
            return;
        }

        System.out.print("Enter number of hours to rent: ");
        int hours = getInt();
        double bill = vehicle.pricePerHour * hours;
        vehicle.isAvailable = false;
        System.out.printf("Vehicle rented successfully. Total bill: ₹%.2f\n", bill);
    }

    static void returnVehicle() {
        System.out.print("Enter vehicle ID to return: ");
        int id = getInt();
        EVehicle vehicle = findVehicleById(id);

        if (vehicle != null && !vehicle.isAvailable) {
            vehicle.isAvailable = true;
            System.out.println("Vehicle returned successfully.");
        } else {
            System.out.println("Invalid ID or vehicle is already available.");
        }
    }

    static EVehicle findVehicleById(int id) {
        for (EVehicle v : vehicles) {
            if (v.id == id) return v;
        }
        return null;
    }

    static int getInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    static double getDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid price: ");
            sc.next();
        }
        return sc.nextDouble();
    }
}
