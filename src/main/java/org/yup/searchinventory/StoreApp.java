package org.yup.searchinventory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static java.util.Comparator.comparing;

public class StoreApp {

    private static ArrayList<Product> inventory = new ArrayList<Product>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        getInventory();

        for (boolean appRunning = true; appRunning; ) {

            System.out.println("What do you want to do?");
            System.out.println("1 - List all products");
            System.out.println("2 - Lookup a product by its id");
            System.out.println("3 - Find all products within a price range");
            System.out.println("4 - Add a new product");
            System.out.println("5 - Quit the application");

            int choice = scanner.nextInt();

            System.out.println();

            switch (choice) {
                case 1:
                    ListProducts();
                    break;
                case 2:
                    LookupProduct();
                    break;
                case 3:
                    FindProductsByPrice();
                    break;
                case 4:
                    AddProduct();
                    break;
                default:
                    System.out.println("Quitting the application");
                    appRunning = false;
                    break;
            }

            System.out.println();

        }

    }

    public static ArrayList<Product> getInventory() {

        try {

            FileReader inventoryReader = new FileReader("inventory.csv");
            BufferedReader inventoryReaderButBetter = new BufferedReader(inventoryReader);

            for(String line = inventoryReaderButBetter.readLine(); line != null; line = inventoryReaderButBetter.readLine()) {

                String[] lineSegments = line.split("\\|");

                int productId = Integer.parseInt(lineSegments[0]);
                String productName = lineSegments[1];
                float productPrice = Float.parseFloat(lineSegments[2]);

                inventory.add(new Product(productId, productName, productPrice));

            }

            inventoryReaderButBetter.close();
            inventoryReader.close();

        } catch (IOException e) {

            System.out.println("IOException, idk");

        }

        Collections.sort(inventory, comparing(Product::getName));

        return inventory;

    }

    public static void ListProducts() {

        System.out.println("We carry the following inventory:");

        for (Product product : inventory) {
            System.out.println(product.asText());
        }

    }

    public static void LookupProduct() {

        System.out.print("Enter product id: ");

        int id = scanner.nextInt();

        for (int i = 0; i < inventory.size(); i++) {
            Product product = inventory.get(i);
            if (product.getId() == id) {
                System.out.println(product.asText());
                return;
            }
        }

        System.out.println("No product has that id");

    }

    public static void FindProductsByPrice() {

        System.out.print("Enter the minimum price: ");
        float minPrice = scanner.nextFloat();

        System.out.print("Enter the minimum price: ");
        float maxPrice = scanner.nextFloat();

        System.out.println("We carry the following inventory within that price range:");

        for (Product product : inventory) {
            float price = product.getPrice();
            if (price > minPrice && price < maxPrice) {
                System.out.println(product.asText());
            }
        }

    }

    public static void AddProduct() {

        try {

            System.out.print("Enter the new product's id: ");
            int id = scanner.nextInt();

            System.out.print("Enter the new product's name: ");
            // BUG: not actually giving the user a chance to input anything.
            String name = scanner.nextLine();

            System.out.print("Enter the new product's price: ");
            float price = scanner.nextFloat();

            inventory.add(new Product(id, name, price));

            Collections.sort(inventory, comparing(Product::getName));

        } catch(Exception e) {

            System.out.println("can't add product. go figure");

        }

    }

}
