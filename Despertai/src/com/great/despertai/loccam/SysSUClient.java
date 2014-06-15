package com.great.despertai.loccam;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import br.ufc.great.syssu.base.Pattern;
import br.ufc.great.syssu.base.Tuple;
import br.ufc.great.syssu.base.interfaces.ISysSUService;

public class SysSUClient {
	private static final String TAG = "AIDLDemo";
	private ISysSUService service;

	private MyServiceConnection connection;
	private Context context;

	private String interesse;

	public SysSUClient(Context context) {

		this.context = context;
		initService();
		interesse = new String("context.device.gpslocation");
		put(interesse);

	}

	class MyServiceConnection implements ServiceConnection {

		public void onServiceConnected(ComponentName name, IBinder boundService) {
			service = ISysSUService.Stub.asInterface(boundService);
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
		}
	}

	@SuppressLint("InlinedApi")
	private void initService() {
		connection = new MyServiceConnection();
		Intent i = new Intent();
		i.setClassName("br.ufc.great.loccam",
				"br.ufc.great.loccam.service.SysSUService");
		boolean ret = context.bindService(i, connection,
				Context.BIND_ABOVE_CLIENT);
		Log.d(TAG, "initService() bound with " + ret);
	}

	public void put(String interesse) {

		// Tupla de interesse em dados
		Tuple t = (Tuple) new Tuple().addField("AppId", "Despertai").addField(
				"InterestElement", interesse);

		// Publica interesse
		try {
			service.put(t);
			Log.i("Loccam-Despertai", "Interesse publicado");
		} catch (RemoteException e) {
			Log.e("Loccam-Despertai", "Erro ao publicar interesse");
			e.printStackTrace();
		}

	}

	public void stop(String interesse) {
		Pattern p = (Pattern) new Pattern().addField("AppId", "Despertai")
				.addField("InterestElement", interesse);
		// Retira interesse
		try {
			service.take(p, null);
			Log.i("Loccam-Despertai", "Interesse retirado");
		} catch (RemoteException e) {
			Log.e("Loccam-Despertai", "Erro ao retirar interesse");
			e.printStackTrace();
		}

	}

	public String get(String interesse) {

		String resultado;
		// Padrão de interesse em dados
		Pattern p = (Pattern) new Pattern().addField("ContextKey", interesse);

		// Lê interesse
		try {
			List<Tuple> tuples = service.read(p, null);
			resultado = (tuples.size() > 0) ? generateString(tuples.get(0))
					: "tuple.size() == 0";
			Log.i("Loccam-Despertai", "Resultado: "+resultado);
		} catch (RemoteException e) {
			Log.e("Loccam-Despertai", "Erro ao ler informação");
			e.printStackTrace();
			resultado = null;
		}
		
		return resultado;
	}

	public static String generateString(List<Tuple> tuples) {
		String r = "";

		if (tuples != null && !tuples.isEmpty()) {
			for (Tuple tuple : tuples) {
				r += generateString(tuple) + "\n\n";
			}

			r = r.substring(0, r.length() - 2);
		} else {
			r = "NULL";
		}

		return r;
	}

	public static String generateString(Tuple tuple) {
		String r = "{\n";
		for (int i = 0; i < tuple.size(); i++) {
			r += "(" + tuple.getField(i).getName() + ","
					+ tuple.getField(i).getValue() + "),\n";
		}
		Log.v("lana", "TupleSize: " + tuple.size());
		r = r.substring(0, r.length() - 2) + "\n}";
		return r;
	}

	public void leGPS(Context context) {
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				Log.i("Loccam-Despertai", "GPS: " + location.getLatitude()
						+ location.getLongitude() + "\n");
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}
}