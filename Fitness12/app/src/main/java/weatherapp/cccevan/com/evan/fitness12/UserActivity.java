package weatherapp.cccevan.com.evan.fitness12;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "UserActivity Tag";
    private static final String GEOFENCE_ID = "GeofenceID";
    private static final int ERROR_DIALOG_REQUEST = 0;
    //instance variable for google api client
    GoogleApiClient googleApiClient = null;

    //sensor
    SensorManager sensorManager;
    //texts for sensor
    TextView tv_steps;
    TextView daily_steps;
    boolean running = false;

    private Button signout;
    private FirebaseAuth mAuth;

    private DatabaseReference database;
    private User currentUserInfo;

    boolean locationPermissionSet=false;

    private GeofencingClient geofencingClient;

    private int totalDistance;
    private int dailyCount;
    private boolean overThousand;
    private int newMark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tv_steps = (TextView)findViewById(R.id.tv_steps);
        daily_steps = (TextView)findViewById(R.id.daily_steps);

        sensorManager  = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAuth = FirebaseAuth.getInstance();

        //load user's  information
        loadUserInfo();

        //set the daily reset of daily steps
        resetDailyValue();



        //sign out of Firebase, if user clicks sign out button
        findViewById(R.id.button_signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(UserActivity.this, MainActivity.class));
                //notify

//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UserActivity.this);
//                Notification notification = new NotificationCompat.Builder(UserActivity.this, "HERCHANNEL1")
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Congrats!")
//                        .setContentText("You did it! You passed 1000 feet!")
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
//                        .build();
//
//                notificationManager.notify(1,notification);
            }
        });
        findViewById(R.id.button_geo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get permissions for location servvices from user
                getLocationPermission();
                StartLocationMonitoring();

                //start monitoring where user location is to see if he/she hit the geofenced area.
                startGeofenceMonitoring();
            }
        });

        //call google api client builder
        googleApiClient = new GoogleApiClient.Builder(this)
                //mention specific api i want to access
                .addApi(LocationServices.API)
                //also handle the callbacks to successful connection and failed connection.
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.d(TAG, "onConnected: Connected to GoogleApiClient");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d(TAG, " Connection suspended to GoogleApiClient");


                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "onConnectionFailed: failed to connect "+ connectionResult.getErrorMessage());
                    }
                })
                .build();

    }
//get permission from user code
    private void getLocationPermission(){
        String[] permissions={ Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                locationPermissionSet = true;
            }
        }
        else{
            ActivityCompat.requestPermissions(this, permissions,1234);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionSet = false;
        switch (requestCode){
            case 1234:{
                if(grantResults.length>0){
                    for(int i =0;i<grantResults.length;i++){
                        locationPermissionSet= false;
                        return;

                    }
                    locationPermissionSet=true;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //connect to google api
        googleApiClient.connect();

        //check if user is already logged into firebase
        //if not logged  in, take them to login page.
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        //        googleApiClient.reconnect();
    }

    @Override
    protected void onStop() {

        super.onStop();

        //if you don't want geofencing  in background (turn it on again in onStart).
//        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor!=null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this,"Sensor not available", Toast.LENGTH_SHORT).show();
        }


        //to make sure  google services are installed on device, check for their existence.
        // if not, download them.
        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(response != ConnectionResult.SUCCESS){
            Log.d(TAG, "onResume: google play services not available, ask user to download it");
            GoogleApiAvailability.getInstance().getErrorDialog(this,response,1).show();
        }
        else{
            Log.d(TAG, "onResume: google play service is available on device.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    //load user info onto the screen.
    private void loadUserInfo(){
        FirebaseUser user1 = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "loadUserInfo: "+database.getKey());
        database=database.child(user1.getUid());


        //check if user is signed in
        if(user1!=null){
            if(database!=null){
                Log.d(TAG, "Database available  ");

                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentUserInfo = dataSnapshot.getValue(User.class);
                        totalDistance = currentUserInfo.getTotalDistance();
                        tv_steps.setText(""+currentUserInfo.getTotalDistance());

                        dailyCount = currentUserInfo.getDailyDistance();
                        daily_steps.setText(""+currentUserInfo.getDailyDistance());

                        newMark=currentUserInfo.getPreviousTotal();
                        overThousand= currentUserInfo.isOverThousand();

                        Log.d("HEY", "onDataChange233: "+currentUserInfo.getEmail());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

            }

        }
    }
    //end of load user info
    //sensor event listener...
    //update database if steps increase.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            tv_steps.setText(String.valueOf(event.values[0]));
            totalDistance = ((int)event.values[0]);
            currentUserInfo.setTotalDistance(totalDistance);
            database.setValue(currentUserInfo);

            //update daily step count
            dailyCount = dailyCount+1;
            daily_steps.setText(dailyCount );
            currentUserInfo.setTotalDistance(dailyCount);

            if(totalDistance>newMark ) {
                overThousand = true;
                newMark = newMark+1000;
            }
            if(overThousand){

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UserActivity.this);
                Notification notification = new NotificationCompat.Builder(UserActivity.this, "HERCHANNEL1")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Congrats!")
                        .setContentText("You did it! You passed 1000 feet!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .build();

                notificationManager.notify(1,notification);
            }
            updateUser(((int)(event.values[0])),dailyCount,overThousand,newMark);


        }

    }

    private void awardNotification() {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
//methods of interface done.



//    update data for user
    public void updateUser(int key, int dailyCount,boolean t, int newMark){
        currentUserInfo.setTotalDistance(key);
        currentUserInfo.setDailyDistance(dailyCount);
        currentUserInfo.setPreviousTotal(newMark);
        currentUserInfo.setOverThousand(t);

        database.setValue(currentUserInfo);
    }


    public boolean isServicesOk(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(UserActivity.this);
        if(available==ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOk: google services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(UserActivity.this, available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Log.d(TAG, "isServicesOk: you cant");

        }
        return false;
    }

//    geofence-activate listener.
    private void StartLocationMonitoring(){
        Log.d(TAG, "StartLocationMonitoring: startlocation called");
        try{
            LocationRequest locationRequest = LocationRequest.create()
                    //interval which app gets update on location
                    .setInterval(10000)
                    .setFastestInterval(5000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            //ask for location updated with location listener
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                @Override
                //location updates are here. 
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: location update "+ location.getLatitude()+" "+location.getLongitude());
                }
            });
        }catch (SecurityException e){
            Log.d(TAG, "StartLocationMonitoring: security exception "+ e.getMessage());
        }



    }
    
    private void startGeofenceMonitoring(){
        Log.d(TAG, "startGeofenceMonitoring: ");
        try{
            //construct geofence
            Geofence geofence = new  Geofence.Builder()
                    .setRequestId(GEOFENCE_ID)
                    .setCircularRegion(33,-81,100)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    //how quickly to respond to geofence
                    .setNotificationResponsiveness(1000)
                    //monitor user exit and entrance
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            Log.d(TAG, "startGeofenceMonitoring: inside try ");

            //then, create geofence request
            GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofence(geofence).build();

            //now we need  pendingIntent  or broadcastreciever
            //done in another class
            //handles events raised

            //we defined intent in geofenceService. now instanciate it
            Intent intent = new Intent(this, GeofenceService.class);
            //pending intent for future use.
            PendingIntent pendingIntent = PendingIntent.getService(this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            //now  connect to google api client
            if(!googleApiClient.isConnected()){
                Log.d(TAG, "startGeofenceMonitoring: GoogleApi Is Not Connected");

            }
            else{
                geofencingClient = LocationServices.getGeofencingClient(this);

//                GeofencingClient client = LocationServices.getGeofencingClient(UserActivity.this);
                geofencingClient.addGeofences(geofencingRequest,pendingIntent).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onResult: successfully added geofence");



                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onResult: failed to add geofence ");

                    }
                });




                //call locationservice geofencingapi
//                LocationServices.GeofencingApi.addGeofences(googleApiClient,geofencingRequest,pendingIntent)
//                        .setResultCallback(new ResultCallback<Status>() {
//                            @Override
//                            public void onResult(@NonNull Status status) {
//                                if(status.isSuccess()){
//                                    Log.d(TAG, "onResult: successfully added geofence");
//                                }
//                                else{
//                                    Log.d(TAG, "onResult: failed to add geofence "+ status.getStatus());
//                                }
//                            }
//                        });



            }
        }catch (SecurityException e){
            Log.d(TAG, "startGeofenceMonitoring: "+e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    //reset daily stats every 24 hours.
    public void resetDailyValue(){


            final ScheduledExecutorService scheduler =
                    Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(
                    new Runnable() {
                        public void run() {
                            dailyCount =0;
                            currentUserInfo.setDailyDistance(dailyCount);
                            database.setValue(currentUserInfo);
                            }
                    }, 86400*1000,1 ,
                    TimeUnit.DAYS
 );
    }





}
