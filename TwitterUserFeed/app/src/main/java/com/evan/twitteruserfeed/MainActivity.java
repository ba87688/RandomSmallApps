package com.evan.twitteruserfeed;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c= this;
        recyclerView = findViewById(R.id.recyclerView);

        //create an instance of the asynctask class
        UserFeed userFeed = new UserFeed();
        userFeed.execute("https://twitrss.me/twitter_user_to_rss/?user=%40caseyneistat");
    }

    //create an inner class that extends asynctask
     class UserFeed extends AsyncTask <String, String, ArrayList<HashMap<String,String>> >{
        String url ="https://twitrss.me/twitter_user_to_rss/?user=%40caseyneistat";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<HashMap<String,String>>  doInBackground(String... params) {
            String site ="https://twitrss.me/twitter_user_to_rss/?user=%40caseyneistat";

            //create an instance to avoid null value
            ArrayList<HashMap<String,String>> results = new ArrayList<>();
            try {
                //create URL object from the url
                URL url = new URL(params[0]);

                //open a connection to the internet
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //set the kind of connection
                connection.setRequestMethod("GET");
                //open the stream of inputs
                InputStream inputStream = connection.getInputStream();

                //parse the xml document
               results= processXML(inputStream);
            } catch (Exception e) {
                Log.d("Exception: ", e+"");
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps) {
//            Log.d("last: ", hashMaps+"");
//            Log.d("things: ", hashMaps.size()+"");
//            Log.d("otherThings: ", hashMaps.get(1).get("title")+"");
//            Log.d("otherThings: ", hashMaps.get(1).get("dc:creator")+"");
//            Log.d("otherThings: ", hashMaps.get(1).get("pubDate")+"");
//
            recyclerView.setAdapter(new TweetAdapter(c,hashMaps));
            recyclerView.setLayoutManager(new LinearLayoutManager(c));

            super.onPostExecute(hashMaps);

        }

        public ArrayList<HashMap<String,String>>  processXML(InputStream is){
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();

                //parse it
                Document xmlDocument = documentBuilder.parse(is);
                //get root element of the xmlDoc. rss root tag
                Element rootElement = xmlDocument.getDocumentElement();
                Log.d("root", rootElement.getTagName());

//                get a node list of all the elements that are of type 'item'
//                this will go through the whole doc and get all the item tags
                NodeList itemsList= rootElement.getElementsByTagName("item");
                //empty list to hold item tag children nodes
                NodeList childOfItem = null;
                //traverse through the nodes returned.

                Node currentItemNode =null;
                Node currentChildOfItemTag =null;

                //hasmap to store strings from the xml doc
                HashMap<String, String> xmlMapValues = null;
                //create an arraylist to store the hashmap for each item tag
                ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
                for(int i = 0;i<itemsList.getLength();i++){
                    //initialize the hashmap
                    xmlMapValues = new HashMap<>();
                    currentItemNode = itemsList.item(i);
//                    Log.d("NODENAME: ", currentItemNode.getNodeName());
                    //get the nodes that are inside of item tag
                    childOfItem = currentItemNode.getChildNodes();
                    //loop through the children tags of 'item' tag
                    for(int n = 0; n<childOfItem.getLength();n++){
                        currentChildOfItemTag = childOfItem.item(n);
//                         Log.d("NODETagChild: ", currentChildOfItemTag.getNodeName());
                        if(currentChildOfItemTag.getNodeName().equalsIgnoreCase("title")){
//                            Log.d("tilteName: ", currentChildOfItemTag.getTextContent());

                            Log.d("title: ", currentChildOfItemTag.getTextContent());
                            xmlMapValues.put("title",currentChildOfItemTag.getTextContent());

                        }
                        if(childOfItem.item(n).getNodeName().equalsIgnoreCase("dc:creator")){
                            xmlMapValues.put("dc:creator",currentChildOfItemTag.getTextContent());
                        }
                        if(childOfItem.item(n).getNodeName().equalsIgnoreCase("pubDate")){
//                            Log.d("image: ", currentChildOfItemTag.getTextContent());
                            xmlMapValues.put("pubDate", currentChildOfItemTag.getTextContent());

                        }


                    }
                    if(xmlMapValues!=null &!xmlMapValues.isEmpty()){
                        arrayList.add(xmlMapValues);
                    }

                }
                return arrayList;

            } catch (Exception e) {
                Log.d("Exception: ", e+"");
            }
            return null;

        }

    }
}
