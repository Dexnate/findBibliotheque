package mk.fr.findbibliothques.dummy.Model;

/**
 * Created by Formation on 18/01/2018.
 */

public class Bibliothèque {
    private String libelle;
    private String ville;
    private int cp;
    private String adresse;
    private Double latitude;
    private Double longitude;


    public Bibliothèque() {
    }

    public String getLibelle() {
        return libelle;
    }

    public Bibliothèque setLibelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public String getVille() {
        return ville;
    }

    public Bibliothèque setVille(String ville) {
        this.ville = ville;
        return this;
    }

    public int getCp() {
        return cp;
    }

    public Bibliothèque setCp(int cp) {
        this.cp = cp;
        return this;
    }

    public String getAdresse() {
        return adresse;
    }

    public Bibliothèque setAdresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Bibliothèque setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Bibliothèque setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }
}
