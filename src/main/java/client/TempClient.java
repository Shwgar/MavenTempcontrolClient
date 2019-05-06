/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.map.ObjectMapper;


public class TempClient {
    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8080/TempcontrollServer").build());
    
    public float getTemp(int hallnr) throws IOException
    {
        
        String sTemp = service.path("rest").path("controlservice/currenttemp").accept(MediaType.TEXT_PLAIN).post(String.class, Integer.toString(hallnr));
        float temp = Float.parseFloat(sTemp);
        return temp;
     
    }
    
        public List<sqlObject> getCurrentStatus() throws IOException
    {
        String jsonString = service.path("rest").path("controlservice/currentstatus").accept(MediaType.APPLICATION_JSON).get(String.class);
        Type datapointListType = new TypeToken<ArrayList<sqlObject>>(){}.getType();
        List<sqlObject> objects = new Gson().fromJson(jsonString, datapointListType);
        return objects;
    }
        
        public List<sqlObject> getDayTemperatures() throws IOException
    {
        String jsonString = service.path("rest").path("controlservice/getdaytemperatures").accept(MediaType.APPLICATION_JSON).get(String.class);
        Type datapointListType = new TypeToken<ArrayList<sqlObject>>(){}.getType();
        List<sqlObject> objects = new Gson().fromJson(jsonString, datapointListType);
        System.out.println(objects);
        return objects;
    }
        
        public List<sqlObject> getDayConsumption() throws IOException
    {
        String jsonString = service.path("rest").path("controlservice/getdayconsumption").accept(MediaType.APPLICATION_JSON).get(String.class);
        Type datapointListType = new TypeToken<ArrayList<sqlObject>>(){}.getType();
        List<sqlObject> objects = new Gson().fromJson(jsonString, datapointListType);
        return objects;
    }
        
        public List<sqlObject> getTotalConsumption() throws IOException
    {
        String jsonString = service.path("rest").path("controlservice/gettotalconsumption").accept(MediaType.APPLICATION_JSON).get(String.class);
        Type datapointListType = new TypeToken<ArrayList<sqlObject>>(){}.getType();
        List<sqlObject> objects = new Gson().fromJson(jsonString, datapointListType);
        return objects;
    }
        
        public String getMaxMinAvgTemp(int hallid) throws IOException
        {
            
            String tempData = service.path("rest").path("controlservice/avgtemp").accept(MediaType.TEXT_PLAIN).post(String.class, Integer.toString(hallid));
            return tempData;
        }
        
        public String getMaxMinAvgCons(int hallid) throws IOException
        {
            
            String consData = service.path("rest").path("controlservice/avgcons").accept(MediaType.TEXT_PLAIN).post(String.class, Integer.toString(hallid));
            return consData;
        }
        
        public void changeTemp(float inTemp, int key) throws IOException
        {
            Message response = new Message();
            response.setValue(inTemp);
            response.setKey(key);
            Gson gsonBuilder = new GsonBuilder().create();
            String sendString = gsonBuilder.toJson(response);
            String sTemp = service.path("rest").path("controlservice/changetemp").accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(String.class, sendString);
            System.out.println(sTemp);
        }
        
        public void changeCost(float inCost)
        {
            String changeCost = Float.toString(inCost);
            String sCost = service.path("rest").path("controlservice/changecost").accept(MediaType.TEXT_PLAIN).post(String.class, changeCost);
            System.out.println(sCost);
        }
 
    
    public float getConsumption(int hallnr) throws IOException
    {
        String sCons = service.path("rest").path("controlservice/currentconsumption").accept(MediaType.TEXT_PLAIN).post(String.class, Integer.toString(hallnr));
        float cons = Float.parseFloat(sCons);
        return cons;
    }
    
        public float getCost() throws IOException
    {
        String sCost = service.path("rest").path("controlservice/cost").accept(MediaType.TEXT_PLAIN).get(String.class);
        float cost = Float.parseFloat(sCost);
        return cost;
    }
}
