package oop.CA5;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.reflect.TypeToken;
import oop.CA5.DTOs.*;

public class Client
{
    /**
     * Main author: Aleksandra Kail
     *
     */
    public static void main(String[] args)
    {

        Client client = new Client();
        client.start();
    }

    public void start()
    {
        try(Socket socket = new Socket("localhost", 8888);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            System.out.println("Client message: The Client is running and has connected to the server");

            Scanner console = new Scanner(System.in);
            boolean running = true;
            String userRequest = "";
            String response;
            JsonConverter jsonConverter = new JsonConverter();
            Order order = new Order();

            while (running)
            {
                System.out.println("1. Display Product by ID");
                System.out.println("2. Display Vendor by ID");
                System.out.println("3. Display Vendors selling chosen Product");
                System.out.println("4. Display Products sold by chosen Vendor");
                System.out.println("5. Display all Products");
                System.out.println("6. Display all Vendors");
                System.out.println("7. Display all Offers");
                System.out.println("8. Add Product to an order");
                System.out.println("9. DeleteProduct from the order");
                System.out.println("10. Place order");
                System.out.println("11.Get Images List");
                System.out.println("12. Exit");
                System.out.println();
                System.out.print("Please enter a command: ");

                userRequest = console.nextLine();
                out.println(userRequest);

                switch (userRequest)
                {
                    case "1":
                    {
                        System.out.print("Enter ID: ");
                        String id = console.next();
                        //Send ID to the server
                        out.println(id);
                        response = in.readLine();
                        Product product = jsonConverter.ConvertJsonStringToObject(response, Product.class);
                        System.out.println("\nResponse from the server: \n" + product + "\n");
                        break;
                    }
                    case "2":
                    {
                        System.out.print("Enter ID: ");
                        String id = console.next();
                        out.println(id);
                        response = in.readLine();
                        Vendor vendor = jsonConverter.ConvertJsonStringToObject(response, Vendor.class);
                        System.out.println("\nResponse from the server: \n" + vendor + "\n");
                        break;
                    }
                    case "3":
                    {
                        System.out.print("Enter ID: ");
                        String id = console.next();
                        out.println(id);
                        response = in.readLine();
                        List<Vendor> vendorList = jsonConverter.convertJsonStringToList(response, Vendor.class);
                        System.out.println("\nResponse from the server:");
                        displayList(vendorList);
                        break;
                    }
                    case "4":
                    {
                        System.out.print("Enter ID: ");
                        String id = console.next();
                        out.println(id);
                        response = in.readLine();
                        List<Product> productList = jsonConverter.convertJsonStringToList(response, Product.class);
                        System.out.println("\nResponse from the server:");
                        displayList(productList);
                        break;
                    }
                    case "5":
                        response = in.readLine();
                        List<Product> productList = jsonConverter.convertJsonStringToList(response, Product.class);
                        System.out.println("\nResponse from the server:");
                        displayList(productList);
                        break;
                    case "6":
                        response = in.readLine();
                        List<Vendor> vendorList = jsonConverter.convertJsonStringToList(response, Vendor.class);
                        System.out.println("\nResponse from the server:");
                        displayList(vendorList);
                        break;
                    case "7":
                        response = in.readLine();
                        List<Offer> offerList = jsonConverter.convertJsonStringToList(response, Offer.class);
                        System.out.println("\nResponse from the server:");
                        displayList(offerList);
                        break;
                    case "8":
                        System.out.print("Enter Product ID: ");
                        String id = console.next();
                        //Send Product ID to the server
                        out.println(id);
                        System.out.print("Enter Vendor ID: ");
                        id = console.next();
                        //Send Vendor ID to the server
                        out.println(id);
                        response = in.readLine();
                        Offer offer = jsonConverter.ConvertJsonStringToObject(response, Offer.class);
                        if (offer != null)
                        {
                            System.out.print("Enter quantity: ");
                            int quantity = console.nextInt();
                            if (quantity <= offer.getQuantity())
                            {
                                order.addItem(offer, quantity);
                                order.printItems();
                            }
                            else
                            {
                                System.out.println("Invalid quantity");
                            }
                        }
                        System.out.println("\nResponse from the server: \n" + offer + "\n");
                        break;
                    case "9":

                        break;
                    case "10":
                        String jsonString = jsonConverter.ConvertObjectToJsonString(order);
                        out.println(jsonString);
                        response = in.readLine();
                        System.out.println("\nResponse from the server: \n" + response + "\n");
                        break;
                    case "11":
                        break;
                    case "12":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid request or error in response\n");
                        break;
                }

                console = new Scanner(System.in);
            }
        }
        catch (IOException e)
        {
            System.out.println("Client message: IOException: " + e);
        }
        System.out.println("Exiting client, but server may still be running.");
    }

    private <T> void displayList(List<T> list)
    {
        for (T object : list)
        {
            System.out.println(object);
        }
        System.out.println();
    }
}