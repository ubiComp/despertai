package br.ufc.great.loccam;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import br.ufc.great.loccam.service.SysSUService;
import br.ufc.great.loccam.service.SysSUStart;
import br.ufc.great.syssu.base.Pattern;
import br.ufc.great.syssu.base.Tuple;
import br.ufc.great.syssu.base.interfaces.IClientReaction;
import br.ufc.great.syssu.base.interfaces.IFilter;
import br.ufc.loccam.adaptation.reasorner.AdaptationReasoner;
import br.ufc.loccam.cacmanager.CACManager;

public class StartLoCCAM {

	// Service State
	public boolean isLoCCAMRunning = false;
	
	// Tag of Service
	private static final String TAG = "Service";
	
	//Objeto para startar o LoCCAM
	private SysSUStart sysSUStart;
	
	//Strings de argumentos para startar o LoCCAM
	private final static String INTERESTELEMENT = "InterestElement";
	private final static String APPID = "AppId";
	private final static String RLOCCAM = "Rloccan";
	

	public void startLoCCAM(Context context, String stringContext, String appId) {
		sysSUStart = new SysSUStart(context, appId);
		putLoCCAM(stringContext);
	}

	public void startLoCCAM(Activity activity) {

		if (!isLoCCAMRunning) {
			Intent startServiceIntent = new Intent(activity, SysSUService.class);
			activity.startService(startServiceIntent);

		} else {
			Intent startServiceIntent = new Intent(activity, SysSUService.class);
			activity.stopService(startServiceIntent);
		}

		isLoCCAMRunning = checkServiceState(activity);

	}

	public void putLoCCAM(String stringContext) {
		Tuple tupla = (Tuple) new Tuple().addField(APPID, RLOCCAM).addField(
				INTERESTELEMENT, "context." + stringContext);
		sysSUStart.put(tupla);
	}

	public List<Tuple> readingLoCCAM(String stringContext) {
		Pattern pattern = (Pattern) new Pattern().addField("ContextKey",
				"context." + stringContext);
		return sysSUStart.read(pattern, null);
	}

	public void takeLoCCAM(String stringContext) {
		Pattern pattern = (Pattern) new Pattern().addField(APPID, RLOCCAM)
				.addField(INTERESTELEMENT, "context." + stringContext);
		sysSUStart.take(pattern, null);
	}

	/**
	 * @param reaction
	 * @param stringContext
	 * @param event
	 * @param ifilter
	 */
	public String subscribeLoCCAM(IClientReaction reaction, String stringContext,
			String event, IFilter ifilter) {
		Pattern pattern = (Pattern) new Pattern().addField("ContextKey",
				"context." + stringContext);

		return sysSUStart.subscribe(reaction, event, pattern, ifilter);
	}
	
	public void unsbscribeLoCCAM(String idSubscribe){
		
		sysSUStart.unSubscribe(idSubscribe);
	}

	/**
	 * Implementar o removeCACList()
	 */
	public void removeCACList() {
		//TODO implementar
	}

	/**
	 * implementar o fillCACList()
	 */
	public void fillCACList() {
		//TODO implementar
	}
	
	/**
	 * Implementar o StopLoCCAM()
	 */
	public void stopLoCCAM(Context context , String stringContext){
		//TODO implementar
		Pattern pattern = (Pattern) new Pattern().addField(APPID, RLOCCAM)
				.addField(INTERESTELEMENT, "context." + stringContext);
		sysSUStart.take(pattern, null);
		Toast.makeText(context, "A aplicação não está mais interessada em nenhuma informação contextual.", Toast.LENGTH_SHORT).show();
	}

	public String readState(Context context) {
		CACManager cacManager = CACManager.getInstance(context);
		AdaptationReasoner adptReasoner = new AdaptationReasoner(CACManager
				.getInstance(context).getListOfAvailableCACs());
		String r;
		r = ((CACManager) cacManager).printBundlesState();
		r += ((AdaptationReasoner) adptReasoner).printDesiredOZ();

		return r;
	}

	public boolean checkServiceState(final Activity activity) {

		ActivityManager manager = (ActivityManager) activity
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			Log.d("Services", service.service.getClassName().toString());
			if (SysSUService.class.getName().equals(
					service.service.getClassName())) {
				Log.d(TAG, "true");
				return true;

			}
		}
		Log.d(TAG, "false");
		return false;

	}

}
