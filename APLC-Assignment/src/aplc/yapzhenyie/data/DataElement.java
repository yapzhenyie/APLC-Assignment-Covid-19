package aplc.yapzhenyie.data;

import java.util.Date;

/**
 *
 * @author Yap Zhen Yie
 */
public class DataElement {
    
    private Date date;
    private int data;

    public DataElement(Date date, int data) {
        this.date = date;
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }   
}
