package com.example.jeremyjohn.dingdong.models;

public class Favourites {
    private int id;
    private String desname;
    private String desno;
    private String deslat;
    private String deslong;
    private String curname;
    private String curno;
    private String curlat;
    private String curlong;

    public Favourites() {
    }

    public Favourites(int id, String desname, String desno, String curname, String curno) {
        this.id = id;
        this.desname = desname;
        this.desno = desno;
        this.curname = curname;
        this.curno = curno;
    }
    public Favourites(int id, String desname, String desno, String deslat, String deslong, String curname, String curno, String curlat, String curlong) {
        this.id = id;
        this.desname = desname;
        this.desno = desno;
        this.deslat = deslat;
        this.deslong = deslong;
        this.curname = curname;
        this.curno = curno;
        this.curlat = curlat;
        this.curlong = curlong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesname() {
        return desname;
    }

    public void setDesname(String desname) {
        this.desname = desname;
    }

    public String getDesno() {
        return desno;
    }

    public void setDesno(String desno) {
        this.desno = desno;
    }

    public String getDeslat() {
        return deslat;
    }

    public void setDeslat(String deslat) {
        this.deslat = deslat;
    }

    public String getDeslong() {
        return deslong;
    }

    public void setDeslong(String deslong) {
        this.deslong = deslong;
    }

    public String getCurname() {
        return curname;
    }

    public void setCurname(String curname) {
        this.curname = curname;
    }

    public String getCurno() {
        return curno;
    }

    public void setCurno(String curno) {
        this.curno = curno;
    }

    public String getCurlat() {
        return curlat;
    }

    public void setCurlat(String curlat) {
        this.curlat = curlat;
    }

    public String getCurlong() {
        return curlong;
    }

    public void setCurlong(String curlong) {
        this.curlong = curlong;
    }
}
