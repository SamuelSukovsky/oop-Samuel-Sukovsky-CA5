package oop.CA5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import oop.CA5.DAOs.*;
import oop.CA5.DTOs.*;
import oop.CA5.Exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;

public class Server
{
    /**
     * Main author: Aleksandra Kail
     * Modified by: Samuel Sukovský
     */
    final int PORT = 8888; //port number to listen for

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start()
    {

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try
        {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started.");
            int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)

            while (true)
            {
                System.out.println("Server: Listening/waiting for connections on port ..." + PORT);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + PORT);

                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                // create a new ClientHandler for the requesting client, passing in the socket and client number,
                // pass the handler into a new thread, and start the handler running in the thread.
                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

            }
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
        finally
        {
            try
            {
                if(clientSocket!=null)
                    clientSocket.close();
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
            try
            {
                if(serverSocket!=null)
                    serverSocket.close();
            }
            catch (IOException e)
            {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
}

class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;
    final String imgDirectory = "src/images";
    private static DataOutputStream dataOutputStream = null;

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber)
    {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        try
        {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run ()
    {
        ProductDaoInterface IProductDao = new MySqlProductDao();
        VendorDaoInterface IVendorDao = new MySqlVendorDao();
        ProductsVendorsDaoInterface IProductsVendorsDao = new MySqlProductsVendorsDao();
        OrderDaoInterface IOrderDao = new MySqlOrderDao();
        OrdersProductsVendorsDaoInterface IOrdersProductsVendorsDao = new MySqlOrdersProductsVendorsDao();
        JsonConverter jsonConverter = new JsonConverter();
        try
        {
            dataOutputStream = new DataOutputStream( clientSocket.getOutputStream());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        int id;
        String jsonString = null;
        String request;
        try
        {
            while  ((request = socketReader.readLine()) != null)
            {
                switch (request)
                {
                    case "1":
                        id = Integer.parseInt(socketReader.readLine());
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request + ", " + id);
                        jsonString = jsonConverter.ConvertObjectToJsonString(IProductDao.getProductById(id));
                        socketWriter.println(jsonString);
                        break;
                    case "2":
                        id = Integer.parseInt(socketReader.readLine());
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request + ", " + id);
                        jsonString = jsonConverter.ConvertObjectToJsonString(IVendorDao.getVendorById(id));
                        socketWriter.println(jsonString);
                        break;
                    case "3":
                        id = Integer.parseInt(socketReader.readLine());
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request + ", " + id);
                        jsonString = jsonConverter.convertListToJsonString(IProductsVendorsDao.getVendorsSellingProductId(id));
                        socketWriter.println(jsonString);
                        break;
                    case "4":
                        id = Integer.parseInt(socketReader.readLine());
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request + ", " + id);
                        jsonString = jsonConverter.ConvertObjectToJsonString(IProductsVendorsDao.getProductsSoldByVendorId(id));
                        socketWriter.println(jsonString);
                        break;
                    case "5":
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                        jsonString = jsonConverter.convertListToJsonString(IProductDao.getAllProducts());
                        socketWriter.println(jsonString);
                        break;
                    case "6":
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                        jsonString = jsonConverter.convertListToJsonString(IVendorDao.getAllVendors());
                        socketWriter.println(jsonString);
                        break;
                    case "7":
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                        jsonString = jsonConverter.convertListToJsonString(IProductsVendorsDao.getAllOffers());
                        socketWriter.println(jsonString);
                        break;
                    case "8":
                        int pid = Integer.parseInt(socketReader.readLine());
                        int vid = Integer.parseInt(socketReader.readLine());
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request + ", " + pid + ", " + vid);
                        jsonString = jsonConverter.ConvertObjectToJsonString(IProductsVendorsDao.getOfferByProductVendorIds(pid, vid));
                        socketWriter.println(jsonString);
                        break;
                    case "10":
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                        String input = socketReader.readLine();
                        Order order = jsonConverter.ConvertJsonStringToObject(input, Order.class);
                        if (order.getOrderId() == -1)
                        {
                            order.setOrderId(IOrderDao.getMaxOrderId() + 1);
                        }
                        try
                        {
                            IOrderDao.insertOrder(order.getOrderId());
                            for (Offer item : order.getItems())
                            {
                                IOrdersProductsVendorsDao.insertOrderItem(order.getOrderId(), item);
                                Offer stock = IProductsVendorsDao.getOfferByProductVendorIds(item.getProductId(), item.getVendorId());
                                IProductsVendorsDao.updateProductsVendorsById(item.getProductId(), item.getVendorId(), stock.getPrice(), stock.getQuantity() - item.getQuantity());
                            }
                            jsonString = "Order placed successfully";
                        }
                        catch (Exception e)
                        {
                            jsonString = e.toString();
                        }
                        finally
                        {
                            socketWriter.println(jsonString);
                        }
                        break;
                    case "11":
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                        id = Integer.parseInt(socketReader.readLine());
                        try
                        {
                            List<Offer> itemList = IOrdersProductsVendorsDao.getItemsByOrderId(id);
                            for (Offer item : itemList)
                            {
                                Offer stock = IProductsVendorsDao.getOfferByProductVendorIds(item.getProductId(), item.getVendorId());
                                IProductsVendorsDao.updateProductsVendorsById(item.getProductId(), item.getVendorId(), stock.getPrice(), stock.getQuantity() + item.getQuantity());
                            }
                            IOrderDao.deleteOrder(id);
                            jsonString = "Order cancelled successfully";
                        }
                        catch (Exception e)
                        {
                            jsonString = e.toString();
                        }
                        finally
                        {
                            socketWriter.println(jsonString);
                        }
                        break;
                    case "12":
                        List<String> imageList = getImageList();
                        socketWriter.println(imageList.toString());
                        String name = socketReader.readLine();
                        String selectedImage = name.substring(name.indexOf(":") + 1);
                        sendFile(imgDirectory + "/" + selectedImage);
                        break;
                    case "9":
                    case "13":
                        System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request + "\nInput is client side only");
                        break;
                    default:
                        socketWriter.println("Invalid option");
                        break;
                }
            }
        }
        catch (IOException | DaoException ex)
        {
            ex.printStackTrace();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            this.socketWriter.close();
            try
            {
                this.socketReader.close();
                this.clientSocket.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }

    public List<String> getImageList()
    {
        List<String> imageList = new ArrayList<>();
        File directory = new File(this.imgDirectory);
        File[] files = directory.listFiles();
        if (files != null)
        {
            for(File file : files)
            {
                if(file.isFile() && file.getName().endsWith(".jpg"))
                {
                    imageList.add(file.getName());
                }
            }
        }
        return imageList;
    }

    private static void sendFile(String path) throws Exception
    {
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        dataOutputStream.writeLong(file.length());

        byte[] buffer = new byte[4 * 1024];

        while ((bytes = fileInputStream.read(buffer))!= -1)
        {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }

        fileInputStream.close();
    }
}
