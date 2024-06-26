package modelo;

public class Impresora {


    //Atributos del objeto
    private String ip;
    private String modelo;
    private String claveRemote;
    private String tender;
    private String serial;


    //Constructor vacio
    public Impresora(){

    }

    //Constructor con todos los atributos
    public Impresora(String ip, String modelo, String tender, String claveRemote) {
        this.ip = ip;
        this.modelo = modelo;
        this.tender = tender;
        this.claveRemote = claveRemote;
    }

    //Declarar Getters
    public String getIp() {
        return ip;
    }

    public String getModelo() {
        return modelo;
    }

    public String getClaveRemote() {
        return claveRemote;
    }

    public String getTender() {
        return tender;
    }

    public String getSerial() {
        return serial;
    }

    //Delcarar Setters
    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setClaveRemote(String claveRemote) {
        this.claveRemote = claveRemote;
    }

    public void setTender(String tender) {
        this.tender = tender;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}