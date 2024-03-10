package oop.CA5.DTOs;

public class Product
{
    private int id;
    private String name;

    public Product(int userId, String name)
    {
        this.id = userId;
        this.name = name;
    }

    public Product(String name)
    {
        this.id = 0;
        this.name = name;
    }

    public Product()
    {
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Product {" + "id = " + id + ", name = '" + name + '\'' + '}';
    }
}
