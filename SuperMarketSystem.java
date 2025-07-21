import java.util.*;

class Product {
    int id;
    String name;
    double price;
    int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    public void display() {
        System.out.printf("ID: %-4d Name: %-15s Price: ₹%.2f  Stock: %d\n", id, name, price, quantity);
    }
}

public class SuperMarketSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Product> products = new ArrayList<>();
    static int productId = 1001;

    public static void main(String[] args) {
        if (!login()) {
            System.out.println("Access denied. Exiting application.");
            return;
        }

        int choice;
        do {
            showMenu();
            choice = getChoice();
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> displayProducts();
                case 3 -> buyProduct();
                case 4 -> removeProduct();
                case 5 -> System.out.println("Exiting application. Thank you.");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);
    }

    private static boolean login() {
        String username, password;
        System.out.println("==== Supermarket Login ====");
        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();

        return username.equals("admin") && password.equals("admin123");
    }

    private static void showMenu() {
        System.out.println("\n==== SuperMarket Menu ====");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Buy Product");
        System.out.println("4. Remove Product");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getChoice() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static void addProduct() {
        sc.nextLine(); // Consume newline
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = readDouble();
        System.out.print("Enter quantity: ");
        int quantity = readInt();

        products.add(new Product(productId++, name, price, quantity));
        System.out.println("Product added successfully.");
    }

    private static void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\nAvailable Products:");
        for (Product p : products) {
            p.display();
        }
    }

    private static void buyProduct() {
        displayProducts();
        if (products.isEmpty()) return;

        System.out.print("Enter product ID to buy: ");
        int id = readInt();
        Product product = findProductById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int qty = readInt();
        if (qty <= 0 || qty > product.quantity) {
            System.out.println("Invalid quantity or out of stock.");
            return;
        }

        double total = product.price * qty;
        product.quantity -= qty;
        System.out.printf("Purchase successful. Total Bill: ₹%.2f\n", total);
    }

    private static void removeProduct() {
        displayProducts();
        if (products.isEmpty()) return;

        System.out.print("Enter product ID to remove: ");
        int id = readInt();
        Product product = findProductById(id);

        if (product != null) {
            products.remove(product);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static Product findProductById(int id) {
        for (Product p : products) {
            if (p.id == id) return p;
        }
        return null;
    }

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid price: ");
            sc.next();
        }
        return sc.nextDouble();
    }
}
