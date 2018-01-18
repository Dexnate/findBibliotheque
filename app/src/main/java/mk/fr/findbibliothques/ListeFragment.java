package mk.fr.findbibliothques;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mk.fr.findbibliothques.dummy.Model.Bibliothèque;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ListeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<Bibliothèque> bibliothequeList;
    private ListView biblioListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toast.makeText(getActivity() , "Fragment appelé", Toast.LENGTH_SHORT).show();
        getDataFromHttp();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liste, container, false);
        biblioListView = view.findViewById(R.id.bibliothequeListView);

        biblioListView.setOnItemClickListener(this);

        return view;

    }

    private void processResponse(String response) {
        //transformation de la réponse json en list de RandomUser
        bibliothequeList = responseToList(response);


        //Conversion de la liste de RandomUser en un tableau de String comportant uniquement
        //le nom des utilisateurs
        String[] data = new String[bibliothequeList.size()];
        for (int i = 0; i < bibliothequeList.size(); i++) {
            data[i] = bibliothequeList.get(i).getLibelle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, data);

        biblioListView.setAdapter(adapter);
    }

    private void getDataFromHttp() {
        String url = "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=bibliotheques-mel&facet=ville&apikey=6a41f9c01ee035a8691b1e933af2d57c18aae5251fa79f7f06b3571f";


        //Définition de la requête
        StringRequest request = new StringRequest(
                //Methode de la requête http
                Request.Method.GET, url,

                //gestionnaire de succès
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTP", response);
                        processResponse(response);

                    }
                },
                //Gestionnaire d'erreurs
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP", error.getMessage());
                    }
                }
        );
        //Ajout de la requete à la file d'execution
        Volley.newRequestQueue(this.getActivity()).add(request);
    }

    private List<Bibliothèque> responseToList(String response){
        List<Bibliothèque> list= new ArrayList<>();
        try {
            JSONObject lilleData = new JSONObject(response);
            JSONArray jsonBiblios = lilleData.getJSONArray("records");
            for(int i=0; i<jsonBiblios.length(); i++){
               JSONObject item=(JSONObject)jsonBiblios.get(i);
                Bibliothèque bibliotheque = new Bibliothèque();


                bibliotheque.setLibelle(item.getJSONObject("fields").getString("libelle"));
                bibliotheque.setVille(item.getJSONObject("fields").getString("ville"));
                bibliotheque.setCp(item.getJSONObject("fields").getInt("code_postal"));
                bibliotheque.setAdresse(item.getJSONObject("fields").getString("adresse"));
                bibliotheque.setLatitude(item.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));
                bibliotheque.setLongitude(item.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));

                //ajout de la bibliotheque à la liste
                list.add(bibliotheque);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
//Recuperation de l'utilisateur sur lequel on vient de cliquer
        Bibliothèque selectedBibliotheque = this.bibliothequeList.get(position);

        //Création d'une intention pour l'affichage de la carte
        Intent mapIntention = new Intent(this.getActivity(), MapsActivity.class);

        //Passage des paramètres
        mapIntention.putExtra("latitude", selectedBibliotheque.getLatitude());
        mapIntention.putExtra("longitude", selectedBibliotheque.getLongitude());

        //Affichage de l'activité
        startActivity(mapIntention);
    }


}

