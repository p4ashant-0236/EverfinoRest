package com.everfino.everfinorest.Models;

public class TableList {
    public int tableid;
    public int tableno;
    public String status;
    public String tableqr;

    public TableList(int tableid, int tableno, String status, String tableqr) {
        this.tableid = tableid;
        this.tableno = tableno;
        this.status = status;
        this.tableqr = tableqr;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public int getTableno() {
        return tableno;
    }

    public void setTableno(int tableno) {
        this.tableno = tableno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTableqr() {
        return tableqr;
    }

    public void setTableqr(String tableqr) {
        this.tableqr = tableqr;
    }
}
