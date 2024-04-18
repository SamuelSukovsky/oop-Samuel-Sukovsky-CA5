package oop.CA5;

import com.google.gson.Gson;
import java.util.List;

public class JsonConverter
{
    private Gson gson;

    public JsonConverter()
    {
        this.gson = new Gson();
    }

    public <Product> String convertProductListToJsonString(List<Product> productList)
    {
        return gson.toJson(productList);
    }

    public <Product> String ConvertProductToJsonString(Product product)
    {
        return gson.toJson(product);
    }

    public <Vendor> String convertVendorListToJsonString(List<Vendor> vendorList)
    {
        return gson.toJson(vendorList);
    }

    public <Vendor> String ConvertVendorToJsonString(Vendor vendor)
    {
        return gson.toJson(vendor);
    }

    public <Offer> String convertOfferListToJsonString(List<Offer> offerList)
    {
        return gson.toJson(offerList);
    }

    public <Offer> String ConvertOfferToJsonString(Offer offer)
    {
        return gson.toJson(offer);
    }
}