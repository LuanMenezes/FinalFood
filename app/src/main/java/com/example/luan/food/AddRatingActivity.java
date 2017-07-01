package com.example.luan.food;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.luan.food.domain.Rating;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddRatingActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Rating> ratings;

    private Rating rating;
    private EditText etDescription;
    private RatingBar rbRating;
    private Button btCreate;
    private ImageView ivPicture;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);

        rating = new Rating();
        etDescription = (EditText) findViewById(R.id.etDescription);
        rbRating = (RatingBar) findViewById(R.id.rbRating);
        ivPicture = (ImageView) findViewById(R.id.ivPicture);
        spinner = (Spinner) findViewById(R.id.spinner);

        btCreate = (Button) findViewById(R.id.btCreate);

        realm = Realm.getDefaultInstance();
        ratings = realm.where( Rating.class ).findAll();
    }

    public void callAddUpdateDiscipline( View view ){

        ratings.sort( "id", RealmResults.SORT_ORDER_DESCENDING );
        long id = ratings.size() == 0 ? 1 : ratings.get(0).getId() + 1;
        rating.setId( id );

        try{
            realm.beginTransaction();

            rating.setDescription(etDescription.getText().toString() );
            rating.setCategory(spinner.getSelectedItemPosition());
            rating.setCreatedAt(new Date());
            rating.setLatitude(10.00);
            rating.setLongitude(10.00);
            rating.setRatingValue(rbRating.getNumStars());
            // TENTAR SETTAR AGORA O CAMINHO DA IMAGEM
            rating.setPicture("asdasdasd");

            realm.copyToRealmOrUpdate( rating );
            realm.commitTransaction();

            Toast.makeText(AddRatingActivity.this, "Voto cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
            finish();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(AddRatingActivity.this, "Não foi possivel cadastrar!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
