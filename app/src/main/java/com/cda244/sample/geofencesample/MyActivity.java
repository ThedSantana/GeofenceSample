package com.cda244.sample.geofencesample;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.ArrayList;


public class MyActivity extends Activity {

	private final MyActivity self = this;
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);

		Log.d("cda244", "onCreate");

		// LocationClient の生成
		Log.d("cda244", "LocationClient make");
		mLocationClient = new LocationClient(this, mConnectionCallbacks, mOnConnectionFailedListener);
		Log.d("cda244", "LocationClient connect");
		mLocationClient.connect();
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("cda244", "onNewIntent " + intent);

		int transitionType = LocationClient.getGeofenceTransition(intent);
		int color = transitionType == Geofence.GEOFENCE_TRANSITION_ENTER ? Color.GREEN : Color.RED;
		findViewById(android.R.id.content).setBackgroundColor(color);
	}


	private void addGeofence() {
		Log.d("cda244", "addGeofence");
		// Geofence の作成
		//final double latitude = 35.697239;
		//final double longitude = 139.774719;

		//35.599240, 139.745541
		double latitude = 35.599240;
		double longitude = 139.745541;

		//35.599101, 139.747107
		//latitude = 35.599101;
		//longitude = 139.747107;

		// 半径(メートル)
		float radius = 100;

		Geofence.Builder builder = new Geofence.Builder();
		builder.setRequestId("geofence_sample");
		builder.setCircularRegion(latitude, longitude, radius);
		builder.setExpirationDuration(Geofence.NEVER_EXPIRE);
		builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);

		ArrayList<Geofence> geofences = new ArrayList<Geofence>();
		geofences.add(builder.build());


		geofences.add(builder.build());

		// PendingIntent の生成
		//Intent intent = new Intent(self, MyActivity.class);
		//PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


		Intent intent = new Intent(this, MyService.class);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Geofences の登録
		mLocationClient.addGeofences(geofences, pendingIntent, mOnAddGeofencesResultListener);
	}


	private GooglePlayServicesClient.ConnectionCallbacks mConnectionCallbacks = new GooglePlayServicesClient.ConnectionCallbacks() {
		@Override
		public void onConnected(Bundle bundle) {
			Log.d("cda244", "geo connect");
			Toast.makeText(self, "onConnected", Toast.LENGTH_LONG).show();
			// Geofenceを登録
			addGeofence();
		}
		@Override
		public void onDisconnected() {
			Log.d("cda244", "geo dis-connect");
			Toast.makeText(self, "onDisconnected", Toast.LENGTH_LONG).show();
		}
	};


	private GooglePlayServicesClient.OnConnectionFailedListener mOnConnectionFailedListener = new GooglePlayServicesClient.OnConnectionFailedListener() {
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			Log.d("cda244", "fail connect");
			Toast.makeText(self, "onConnectionFailed", Toast.LENGTH_LONG).show();
		}
	};

	private LocationClient.OnAddGeofencesResultListener mOnAddGeofencesResultListener = new LocationClient.OnAddGeofencesResultListener() {
		@Override
		public void onAddGeofencesResult(int i, String[] strings) {
			Log.d("cda244", "geo OnAddGeofencesResultListener");
			Toast.makeText(self, "onAddGeofencesResult", Toast.LENGTH_LONG).show();
		}
	};

}

