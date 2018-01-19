package mk.fr.findbibliothques;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView libelleTextView;
    private TextView villeTextView;
    private TextView cpTextView;
    private TextView adresseTextView;

    private String libelle;
    private  String ville;
    private String cp;
    private String adresse;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Récupération des widgets pour traitement
        libelleTextView = findViewById(R.id.libelleTextView);
        villeTextView = findViewById(R.id.villeTextView);
        cpTextView = findViewById(R.id.cpTextView);
        adresseTextView = findViewById(R.id.adresseTextView);

        //Récuperation des données passées à l'activité
        Bundle params = getIntent().getExtras();

        libelle = params.getString("libelle");
        ville = params.getString("ville");
        cp = params.getString("cp");
        adresse =params.getString("adresse");
        latitude = params.getDouble("latitude");
        longitude = params.getDouble("longitude");


        //Transmission des données aux widgets correspondants
        libelleTextView.setText(libelle);
        villeTextView.setText(ville);
        cpTextView.setText(cp);
        adresseTextView.setText(adresse);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(libelle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(14.0f);
    }
}
