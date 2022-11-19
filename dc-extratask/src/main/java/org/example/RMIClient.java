package org.example;

import java.rmi.Naming;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class RMIClient {
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/Service";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            Instant ts = Instant.now();
            while (true) {
                Integer n = service.pollElem();
                //int fibCount = service.pollElem();
                if (n == null) {
                    System.out.println("Received none!");
                    break;
                } else {
                    ArrayList<Double> arr = new ArrayList<>();
                    for (int i = 1; i <= 9999; i++) {
                        arr.add((double) i);
                    }
                    double sumOfElements = 0;
                    for (Double element : arr) {
                        sumOfElements += element;
                    }
                    double value = 0;
                    for (int i = 0; i < arr.size(); i++) {
                        value = (Math.sin(i) + Math.cos(i));
                    }
                    double res = value * sumOfElements;
                    service.computeTheValue(res);
                }
            }
            Instant te = Instant.now();
            System.out.println("Time = " + Duration.between(ts, te).toNanos() / 1e9 + " sec.");

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}