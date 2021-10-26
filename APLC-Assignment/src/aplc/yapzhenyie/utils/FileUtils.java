/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import aplc.yapzhenyie.APLCAssignment;
import aplc.yapzhenyie.data.Country;
import aplc.yapzhenyie.data.DataElement;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yap Zhen Yie
 */
public class FileUtils {

    public static List<Country> readCSVFile(String filePath) {
        List<Country> countries = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(filePath);
            InputStreamReader inputStream = new InputStreamReader(file, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(inputStream);

            String[] rows;
            List<String> headers = new ArrayList<>();
            boolean isHeader = true; //Header

            int error = 0;
            while ((rows = reader.readNext()) != null) {
                if (isHeader) {
                    for (String header : rows) {
                        headers.add(header);
                    }
                    isHeader = false;
                    continue;
                }

                Country newCountry = new Country();

                for (int col = 0; col < rows.length; col++) {
                    Object header = headers.get(col);

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
                            DataElement dataEle = new DataElement(DateTimeHelper.parseDate((String) header), rowData);
                            // Is second element & above
                            if (col > 4 && rowData > 0) {
                                int previousRowData = Integer.valueOf(rows[col - 1]);
                                int newRowData = rowData - previousRowData;
                                if (previousRowData > 0) {
                                    // The data reduce in the next day (Due to wrong dataset)
                                    if (newRowData < 0) {
                                        //APLCAssignment.addErrorLogMessage("Country: " + newCountry.getCountryName()
                                        //        + (newCountry.getStateName() == null ? "" : (" - " + newCountry.getStateName())) + " (Date: " + header + ") The data is less than the previous day. (" + newRowData + ")");
                                        //error++;
                                        // dataEle.setData(0);
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
            if (error > 0) {
                APLCAssignment.addLogMessage("Total " + error + " errors were found in " + filePath + " file. The value of the specific date will be marked as 0.");
            }
            return countries;
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Country> readCSVFileFromUrl(String urlPath) {
        List<Country> countries = new ArrayList<>();
        try {
            URL url = new URL(urlPath);
            URLConnection urlConnec = url.openConnection();
            InputStreamReader inputStream = new InputStreamReader(urlConnec.getInputStream(), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(inputStream);

            String[] rows;
            List<String> headers = new ArrayList<>();
            boolean isHeader = true; //Header

            while ((rows = reader.readNext()) != null) {
                if (isHeader) {
                    for (String header : rows) {
                        headers.add(header);
                    }
                    isHeader = false;
                    continue;
                }

                Country newCountry = new Country();

                for (int col = 0; col < rows.length; col++) {
                    Object header = headers.get(col);

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
                            DataElement dataEle = new DataElement(DateTimeHelper.parseDate((String) header), rowData);
                            // Is second element & above
                            if (col > 4 && rowData > 0) {
                                int previousRowData = Integer.valueOf(rows[col - 1]);
                                int newRowData = rowData - previousRowData;
                                if (previousRowData > 0) {
                                    // The data reduce in the next day (Due to wrong dataset)
                                    if (newRowData < 0) {
                                        // dataEle.setData(0);
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
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
