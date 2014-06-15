package com.great.despertai.loccam;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import br.ufc.great.syssu.base.Pattern;
import br.ufc.great.syssu.base.Tuple;
import br.ufc.great.syssu.base.interfaces.ISysSUService;

public class SysSUClient {

	private static final String TAG = "Loccam-Despertai";
	private ISysSUService service;
	private MyServiceConnection connection;
	private Context context;
	private String interesse;
	private Location location;

	public SysSUClient(Context context) {

		this.context = context;
		if (this.context == null)
			Log.v(TAG, "problema");
		initService();
		interesse = new String("context.device.gpslocation");
		location = new Location(interesse);
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
				"InterestElement", "context.device.gpslocation");

		// Publica interesse
		try {
			service.put(t);
			Log.i("Loccam-Despertai", "Interesse publicado");
		} catch (NullPointerException e) {
			Log.e("Loccam-Despertai", "Erro ao publicar interesse");
			e.printStackTrace();
		} catch (RemoteException e) {
			Log.e("Loccam-Despertai", "Erro ao publicar interesse");
			e.printStackTrace();
		}

	}

	public void put() {
		put(this.interesse);
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

	public void stop() {
		stop(this.interesse);
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

			setLocation(tuples.size() > 0 ? ""
					+ tuples.get(0).getField(2).getValue() : " ");
			Log.i("Loccam-Despertai", "Resultado Loccam: " + resultado);
		} catch (RemoteException e) {
			Log.e("Loccam-Despertai", "Erro ao ler informação");
			e.printStackTrace();
			resultado = null;
		}

		return resultado;
	}

	public String get() {
		return get(this.interesse);
	}

	private static String generateString(Tuple tuple) {
		String r = "{\n";
		for (int i = 0; i < tuple.size(); i++) {
			r += "(" + tuple.getField(i).getName() + ","
					+ tuple.getField(i).getValue() + "),\n";
		}
		Log.v(TAG, "TupleSize: " + tuple.size());
		r = r.substring(0, r.length() - 2) + "\n}";
		return r;
	}

	private void setLocation(String string) {

		String[] s = string.split(",");
		s[0] = s[0].replace("[", " ").trim();
		s[1] = s[1].trim();
		s[3] = s[0].replace("]", " ").trim();

		Log.v(TAG, "Latitude: " + s[0] + " Longitude: " + s[1]);
		Log.v(TAG, "Altitude: " + s[2] + " Accuracy: " + s[3]);
		location.setLatitude(Double.parseDouble(s[0]));
		location.setLongitude(Double.parseDouble(s[1]));
		location.setAltitude(Double.parseDouble(s[2]));
		location.setAccuracy(Float.parseFloat(s[3]));
	}

	public Location getLocation() {
		return this.location;
	}

	public String getCity() {
		Geocoder gcd = new Geocoder(context, Locale.ENGLISH);
		String city = new String();
		try {
			List<Address> addresses = gcd.getFromLocation(location.getLatitude(),
					location.getLongitude(), 1);
			if (addresses != null) {
				Log.v(TAG, "Cidade:" + addresses.get(0).getAddressLine(1));
				Toast.makeText(context, "Cidade:"
						+ addresses.get(0).getAddressLine(1), Toast.LENGTH_LONG).show();
				city = addresses.get(0).getAddressLine(1);
			}
		} catch (IOException e) {
			Log.e(TAG, "Problema para definir a cidade");
			e.printStackTrace();
			city = "";
		}
		return city;
	}

}