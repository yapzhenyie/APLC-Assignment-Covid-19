/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.data.DataElement;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Yap Zhen Yie
 */
public class FileUtils {

    public static List<Country> readCSVFile(String filePath) throws MalformedURLException, IOException,
            CsvValidationException, ParseException {
        List<Country> countries = new ArrayList<>();
        FileInputStream file = new FileInputStream(filePath);
        InputStreamReader inputStream = new InputStreamReader(file, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(inputStream);

        String[] rows;
        List<String> headers = new ArrayList<>();
        boolean isHeader = true; //Header

        while ((rows = reader.readNext()) != null) {
            if (isHeader) {
                headers.addAll(Arrays.asList(rows));
                isHeader = false;
                continue;
            }

            Country newCountry = new Country();

            for (int col = 0; col < rows.length; col++) {
                String header = headers.get(col);

                String data = rows[col];

                switch (col) {
                    case 0:
                        if (!data.isEmpty() && !data.equals("")) {
                            newCountry.setStateName(data);
                        }
                        break;
                    case 1:
                        newCountry.setCountryName(data);
                        break;
                    case 2:
                        if (!data.isEmpty()) {
                            newCountry.setLatitude(Float.valueOf(data));
                        }
                        break;
                    case 3:
                        if (!data.isEmpty()) {
                            newCountry.setLongitude(Float.valueOf(data));
                        }
                        break;
                    default:
                        int rowData = Integer.valueOf(data);
                        DataElement dataEle = new DataElement(DateTimeHelper.parseDate(header), rowData);
                        // Is second element & above
                        if (col > 4 && rowData > 0) {
                            int previousRowData = Integer.valueOf(rows[col - 1]);
                            int newRowData = rowData - previousRowData;
                            if (previousRowData > 0) {
                                // The data reduce in the next day (Due to wrong dataset)
                                if (newRowData < 0) {
                                    // Remain the wrong data to avoid wrong counting in sum.
                                    dataEle.setData(newRowData);
                                } else {
                                    dataEle.setData(newRowData);
                                }
                            }
                        }
                        newCountry.getDataset().add(dataEle);
                }
            }
            countries.add(newCountry);
        }
        return countries;
    }

    public static List<Country> readCSVFileFromUrl(String urlPath) throws MalformedURLException, IOException,
            CsvValidationException, ParseException {
        List<Country> countries = new ArrayList<>();
        URL url = new URL(urlPath);
        URLConnection urlConnection = url.openConnection();
        InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(inputStream);

        String[] rows;
        List<String> headers = new ArrayList<>();
        boolean isHeader = true; //Header

        while ((rows = reader.readNext()) != null) {
            if (isHeader) {
                headers.addAll(Arrays.asList(rows));
                isHeader = false;
                continue;
            }

            Country newCountry = new Country();

            for (int col = 0; col < rows.length; col++) {
                String header = headers.get(col);

                String data = rows[col];

                switch (col) {
                    case 0:
                        if (!data.isEmpty() && !data.equals("")) {
                            newCountry.setStateName(data);
                        }
                        break;
                    case 1:
                        newCountry.setCountryName(data);
                        break;
                    case 2:
                        if (!data.isEmpty()) {
                            newCountry.setLatitude(Float.valueOf(data));
                        }
                        break;
                    case 3:
                        if (!data.isEmpty()) {
                            newCountry.setLongitude(Float.valueOf(data));
                        }
                        break;
                    default:
                        int rowData = Integer.valueOf(data);
                        DataElement dataEle = new DataElement(DateTimeHelper.parseDate(header), rowData);
                        // Is second element & above
                        if (col > 4 && rowData > 0) {
                            int previousRowData = Integer.valueOf(rows[col - 1]);
                            int newRowData = rowData - previousRowData;
                            if (previousRowData > 0) {
                                // The data reduce in the next day (Due to wrong dataset)
                                if (newRowData < 0) {
                                    // Remain the wrong data to avoid wrong counting in sum.
                                    dataEle.setData(newRowData);
                                } else {
                                    dataEle.setData(newRowData);
                                }
                            }
                        }
                        newCountry.getDataset().add(dataEle);
                }
            }
            countries.add(newCountry);
        }
        return countries;
    }
}
