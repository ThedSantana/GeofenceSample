package com.cda244.sample.geofencesample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	@Override
	public void onCreate() {
		Log.d("cda244", "サービス onCreate " );

		doSomething();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//明示的にサービスの起動、停止が決められる場合の返り値
		Log.d("cda244", "サービス onStartCommand ");

/*

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("cda244", "onNewIntent " + intent);

		int transitionType = LocationClient.getGeofenceTransition(intent);
		int color = transitionType == Geofence.GEOFENCE_TRANSITION_ENTER ? Color.GREEN : Color.RED;
		findViewById(android.R.id.content).setBackgroundColor(color);
	}


 */


		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d("cda244", "サービス onDestroy ");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void doSomething()
	{
		Log.d("cda244", "サービス do "+getBaseContext() );
		this.stopSelf();

	}

}

