package com.everfino.everfinorest.Models;

public class MenuList {

    public int itemid;
    public String itemname;
    public int itemprice;

    public MenuList(int itemid, String itemname, int itemprice, String itemdesc, String itemtype) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemdesc = itemdesc;
        this.itemtype = itemtype;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String itemdesc;
    public String itemtype;


    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getItemprice() {
        return itemprice;
    }

    public void setItemprice(int itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }
}
