package com.example.geeksera.jukebox.Objects;

/**
 * Created by GeeksEra on 1/26/2018.
 */

public class Request {
    String Purchasedate,Purchasedby,T_id,TableNumber,Tcount,Tokenstatus,Tokentitle_id;

    public String getPurchasedate() {
        return Purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        Purchasedate = purchasedate;
    }

    public String getPurchasedby() {
        return Purchasedby;
    }

    public void setPurchasedby(String purchasedby) {
        Purchasedby = purchasedby;
    }

    public String getT_id() {
        return T_id;
    }

    public void setT_id(String t_id) {
        T_id = t_id;
    }

    public String getTableNumber() {
        return TableNumber;
    }

    public void setTableNumber(String tableNumber) {
        TableNumber = tableNumber;
    }

    public String getTcount() {
        return Tcount;
    }

    public void setTcount(String tcount) {
        Tcount = tcount;
    }

    public String getTokenstatus() {
        return Tokenstatus;
    }

    public void setTokenstatus(String tokenstatus) {
        Tokenstatus = tokenstatus;
    }

    public String getTokentitle_id() {
        return Tokentitle_id;
    }

    public void setTokentitle_id(String tokentitle_id) {
        Tokentitle_id = tokentitle_id;
    }
}
