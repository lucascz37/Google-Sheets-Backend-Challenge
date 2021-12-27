package com.lucas.googleSheets.challenge.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.lucas.googleSheets.challenge.model.Consumer;
import com.lucas.googleSheets.challenge.model.ConsumerFilterData;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SheetService {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String range = "A:AC";
    private final Sheets service;

    public SheetService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private List<Consumer> getList() throws IOException {

        String spreadsheetId = "1NaCxxEAVv2DZ7a0Y2cnIB4QhroQRcOIN3nis56vCAM4"; // Replace if needed
        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();

        return result.getValues().stream().skip(1).map(Consumer::new).collect(Collectors.toList());
    }

    public ConsumerFilterData getConsumersBornBetween() throws IOException {
        List<Consumer> consumers = getList();
        List<Consumer> filtered = consumers.stream().
                filter(e -> e.getYearBirth() >= 1957 && e.getYearBirth() <= 1967 ).collect(Collectors.toList());
        return generateFilterData(consumers, filtered);
    }

    public ConsumerFilterData getConsumersWithMasters() throws IOException {
        List<Consumer> consumers = getList();
        List<Consumer> filtered = consumers.stream()
                .filter(e -> e.getEducation().equals("Master")).collect(Collectors.toList());
        return generateFilterData(consumers, filtered);
    }

    public ConsumerFilterData getMarriedConsumers() throws IOException {
        List<Consumer> consumers = getList();
        List<Consumer> filtered = consumers.stream().filter(e -> e.getMaritalStatus().equals("Married")).collect(Collectors.toList());
        return generateFilterData(consumers, filtered);
    }

    public ConsumerFilterData getConsumersWithKids() throws IOException {
        List<Consumer> consumers = getList();
        List<Consumer> filtered = consumers.stream().filter(e -> e.getKidHome() > 0).collect(Collectors.toList());
        return generateFilterData(consumers, filtered);
    }

    public ConsumerFilterData getConsumersWithTeens() throws IOException {
        List<Consumer> consumers = getList();
        List<Consumer> filtered = consumers.stream().filter(e -> e.getTeenHome() > 0).collect(Collectors.toList());
        return generateFilterData(consumers, filtered);
    }

    private ConsumerFilterData generateFilterData(List<Consumer> consumers, List<Consumer> filtered){
        return new ConsumerFilterData(consumers.size(), filtered.size(),
                (filtered.size() / (double) consumers.size()) * 100, filtered);
    }
}

