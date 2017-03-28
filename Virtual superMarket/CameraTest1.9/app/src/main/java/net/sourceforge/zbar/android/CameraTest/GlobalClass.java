package net.sourceforge.zbar.android.CameraTest;

/**
 * Created by Drashti on 3/5/2016.
 */

    public class GlobalClass {


    private static GlobalClass instance;

    // Global variable
    private String custid;

    // Restrict the constructor from being instantiated
    private GlobalClass() {
    }

    public void setData(String d) {
        this.custid = d;
    }

    public String getData() {
        return this.custid;
    }

    public static synchronized GlobalClass getInstance() {
        if (instance == null) {
            instance = new GlobalClass();
        }
        return instance;


    }
}
