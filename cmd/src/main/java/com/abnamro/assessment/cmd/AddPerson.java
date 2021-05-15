package com.abnamro.assessment.cmd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import com.abnamro.assessment.cmd.utils.Utils;

/**
 * It is a simple client to POST a Person to a service
 * TODO: Error/Exception handling/messaging can be improved if necessary
 * 
 * Usage:
 *      mvn exec:java -Dexec.mainClass="com.abnamro.assessment.cmd.AddPerson" -Dexec.args="Fred2 2003-03-21"
 *      or
 *      mvn exec:java -Dexec.mainClass="com.abnamro.assessment.cmd.AddPerson" -Dexec.args="Fred2 2003-03-21 http://localhost:8080/persons"
 *      (a service URL can be passed as a third argument. Otherwise a default URL will be used)
 * 
 * Note: Java 11 HttpClient can be used here. But in main pom the version 1.8 is specified.
 *       It is expected that the same java-version (1.8) should be used accross all the project
 * 
 */

public class AddPerson {
    private static final Logger LOGGER = Logger.getLogger(GetPersons.class.getName());
    private static final String defaultURL = "http://localhost:8080/persons";

    public static void main( String[] args ) throws IOException {
        LOGGER.info( "--- AddPerson ---");
        if(args.length < 2){
            LOGGER.info("[firstname] and [birthdate] must be provided");
            LOGGER.info("Usage:");
            LOGGER.info("mvn exec:java -Dexec.mainClass=\"com.abnamro.assessment.cmd.AddPerson\" -Dexec.args=\"firstname birthdate\"");
            return;
        }
        String srvUrl = args.length > 2 ? args[2] : defaultURL;
        LOGGER.info(String.format("Used URL: [%s]", srvUrl));

        URL url = new URL(srvUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);
        con.setConnectTimeout(2000);
        con.setReadTimeout(2000);

        String jsonInputString = Utils.createJsonBody(args[0], args[1]);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try {
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                LOGGER.info(response.toString());
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
        con.disconnect();
        LOGGER.info("--- Done ---");
        
    }

}