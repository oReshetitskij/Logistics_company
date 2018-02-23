import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String fromAddr = "Украина, Киев, улица Политехническая, 11";
    private static final String toAddr = "Украина, Киев, улица Пушкина, 5";
    private static final TravelMode travelMode = TravelMode.WALKING;

    private static GeoApiContext context;

    public static void main(String[] args) {
        DirectionsPrev();
        GeocodingPrev();
        DistanceMatrixPrev();
    }

    public static void DirectionsPrev() {
        final String API_KEY = "AIzaSyBHUNATkvBBtqWEF8roNL_EJEF-IQ1THP0";
        context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DirectionsApiRequest req = DirectionsApi.newRequest(context);

        DirectionsResult result = null;
        try {
            result = req.origin(fromAddr)
                    .destination(toAddr)
                    .mode(travelMode)
                    .alternatives(true)
                    .avoid(DirectionsApi.RouteRestriction.TOLLS)
                    .avoid(DirectionsApi.RouteRestriction.FERRIES)
                    .avoid(DirectionsApi.RouteRestriction.HIGHWAYS)
                    .units(Unit.METRIC)
                    .departureTime(new DateTime())
                    .await();

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectionsRoute[] routes = result.routes;
        System.out.println("routes via: "+travelMode+
                "\nfrom: "+fromAddr+
                "\nto: "+toAddr+
                "\nis:");
        for(int i = 0; i < routes.length; ++i){
            DirectionsLeg[] legs = routes[i].legs;
            System.out.println("\nroute #"+(i+1)+": ");
            System.out.println("summary "+routes[i].summary);
            for(int l = 0; l < legs.length; ++l )
                System.out.println(legs[l].distance+" "+legs[l].duration);

            List<LatLng> latLngs = routes[i].overviewPolyline.decodePath();
            for(LatLng ll : latLngs)
                System.out.println(ll);
                //System.out.println(AutonomusLatLngToAdress(ll)+"\t"+ll); USE CAREFULLY
        }
        System.out.println();
    }

    public static void GeocodingPrev() {
        final String API_KEY = "AIzaSyASCAb7p_gBcpp5IPISi6mqT719x_oZARo";
        context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        GeocodingApiRequest req = GeocodingApi.newRequest(context);

        GeocodingResult result[] = null;
        try {
            result = req.region("Ukraine")
                    .address(fromAddr)
                    .resultType(AddressType.STREET_ADDRESS)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String location = result[0].formattedAddress;
        LatLng geoLocation = result[0].geometry.location;
        System.out.println("addres of: "+fromAddr+
                "\nis: "+location+
                "\ngeo location is: "+geoLocation+"\n");
    }

    public static void  DistanceMatrixPrev(){
        final String API_KEY = "AIzaSyBHUNATkvBBtqWEF8roNL_EJEF-IQ1THP0";

        context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);

        DistanceMatrix result = null;
        try {
            result = req.origins(fromAddr)
                    .destinations(toAddr)
                    .mode(travelMode)
                    .avoid(DirectionsApi.RouteRestriction.TOLLS)
                    .avoid(DirectionsApi.RouteRestriction.FERRIES)
                    .avoid(DirectionsApi.RouteRestriction.HIGHWAYS)
                    .language("ru-RU")
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long distApart = result.rows[0].elements[0].distance.inMeters;
        System.out.println("distance via: "+travelMode+
                "\nfrom: "+fromAddr+
                "\nto: "+toAddr+
                "\nis:"+distApart/1000d+"km\n");
    }

    public static String AutonomusLatLngToAdress(LatLng geoLocation){
        final  String API_KEY = "AIzaSyASCAb7p_gBcpp5IPISi6mqT719x_oZARo";
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();


        GeocodingApiRequest req = GeocodingApi.newRequest(context);

        GeocodingResult result[] = null;
        try {
            result = req.region("Ukraine")
                    .latlng(geoLocation)
                    .resultType(AddressType.STREET_ADDRESS)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result[0].formattedAddress;
    }
}
