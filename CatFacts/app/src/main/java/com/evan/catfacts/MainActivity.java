package com.evan.catfacts;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity {
    private Button jokeButton;
    private TextView jokeText;
    private ImageView catImage;
    private boolean networkCool;
    String url;
    //https://thecatapi.com/api/images/get?format=json&resultsperpage=1
   // https://thecatapi.com/api/images/get?resultsperpage=1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        networkCool = NetworkHelper.hasNetwork(this);
        Log.d("HAS Network? " ,": " +networkCool);

        jokeButton = (Button) findViewById(R.id.jokeButton);
        jokeText= (TextView)findViewById(R.id.jokeText);
        catImage = (ImageView) findViewById(R.id.catImage);
        url = "https://thecatapi.com/api/images/get?resultsperpage=1";

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCatImage(url);
                creatNewJoke();
            }
        });
    }

    private void loadCatImage(String r) {

        Ion.with(this).
                load("https://api.thecatapi.com/api/images/get?format=xml&resultsperpage=1").
                asString().
                setCallback(new FutureCallback<String>() {


                    @Override
                    public void onCompleted(Exception e, String result) {
                        //data has arrived
                        try {
                            XmlToJson xmlToJson = new XmlToJson.
                                    Builder(result).
                                    build();
                            JSONObject jsonObject = xmlToJson.toJson();
                            //get the "text" from Json object
                            Log.d("i","HI"+jsonObject.toString(4));

                            //data of json object will look like this
                            /*
                            {
                                "response": {
                                    "data": {
                                        "images": {
                                            "image": {
                                                    "id": "v",
                                                    "source_url": "https:\/\/thecatapi.com\/?image_id=v",
                                                    "url": "https:\/\/29.media.tumblr.com\/tumblr_krvvj0ZbSA1qa9hjso1_500.jpg"
                                            }
                                        }
                                    }
                                }
                            }
                               */

                            //get picture url
                            String u = jsonObject.getJSONObject("response").
                                    getJSONObject("data").
                                    getJSONObject("images").
                                    getJSONObject("image").
                                    getString("url");

                            //use picasso to load picture ur into imageview
                            Picasso.get().load(u).into(catImage);


                        }
                        catch (JSONException jsonException){
                            Log.wtf("help", jsonException);
                        }
                    }
                });

    }

    private void creatNewJoke() {
        Ion.with(this).
                load("https://cat-fact.herokuapp.com/facts/random?animal=cat&amount=1").
                asString().
                setCallback(new FutureCallback<String>() {
                    //this will be the return, a json object
                  /*
                    {"_id":"591f98783b90f7150a19c1c7","__v":0,
                      "text":"It is estimated that cats can make over 60 different sounds.",
                      "updatedAt":"2018-12-05T05:56:30.384Z",
                      "createdAt":"2018-05-15T20:20:02.794Z",
                      "deleted":false,
                      "type":"cat",
                      "source":"api",
                      "used":false}
                      */

                    @Override
                    public void onCompleted(Exception e, String result) {
                        //data has arrived
                        try {
                            //create a json object of the JSON data
                            JSONObject jsonObject = new JSONObject(result);
                            //get the "text" from Json object
                            String joke = jsonObject.getString("text");

                            //set the textview to show text of joke
                            jokeText.setText(joke);



                        }
                        catch (JSONException jsonException){
                            Log.wtf("help", jsonException);
                        }
                    }
                });




    }
}
