package aplc.yapzhenyie.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yap Zhen Yie
 */
public class Country {
    
    private String countryName;
    private String stateName;
    private float latitude;
    private float longitude;
    private List<DataElement> dataset;

    public Country() {
        this.dataset = new ArrayList<>();
    }
    
    public Country(String countryName, String stateName, float latitude, float longitude) {
        this.countryName = countryName;
        this.stateName = stateName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataset = new ArrayList<>();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public List<DataElement> getDataset() {
        return dataset;
    }

    public void setDataset(List<DataElement> dataset) {
        this.dataset = dataset;
    }
}
