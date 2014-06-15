package br.ufc.great.loccam.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;
import br.ufc.great.loccam.LoCCAM;
import br.ufc.great.syssu.base.Pattern;
import br.ufc.great.syssu.base.Tuple;
import br.ufc.great.syssu.base.TupleSpaceException;
import br.ufc.great.syssu.base.TupleSpaceSecurityException;
import br.ufc.great.syssu.base.interfaces.IClientReaction;
import br.ufc.great.syssu.base.interfaces.IFilter;
import br.ufc.great.syssu.base.interfaces.ILocalDomain;
import br.ufc.great.syssu.ubibroker.LocalUbiBroker;
import br.ufc.loccam.adaptation.reasorner.AdaptationReasoner;
import br.ufc.loccam.cacmanager.CACManager;

public class SysSUStart {

	private LocalUbiBroker localUbiBroker;
	private ILocalDomain domain;
	private LoCCAM loCCAM;
	private String appId;
	private ArrayList<String> interests = new ArrayList<String>();
	private ArrayList<String> reactionIds = new ArrayList<String>();

	public SysSUStart(Context context, String appId) {
		this.appId = appId;
		Toast.makeText(context.getApplicationContext(),
				"SysSU Service started.", Toast.LENGTH_LONG).show();
		try {
			localUbiBroker = LocalUbiBroker.createUbibroker();
			domain = (ILocalDomain) localUbiBroker.getDomain("loccam");
		} catch (Exception e) {
			e.printStackTrace();
		}

		loCCAM = new LoCCAM(domain, CACManager.getInstance(context),
				new AdaptationReasoner(CACManager.getInstance(context)
						.getListOfAvailableCACs()));
	}

	public void put(Tuple tuple) {
		try {
			domain.put(tuple, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void putInterest(String key){
		
		try{	
			Tuple tupla = (Tuple) new Tuple()
			.addField("AppId", appId)
			.addField("InterestElement", key);
			put(tupla);
			interests.add(key);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Tuple> read(Pattern pattern, IFilter filter) {
		List<Tuple> tuples = null;
		try {
			tuples = domain.read(pattern, filter, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tuples;
	}
	
	public Tuple read(String key){
		
		try{
			Pattern pattern1 = (Pattern) new Pattern().addField("ContextKey", key);
			List<Tuple> lista = read(pattern1, null);
			if(!lista.isEmpty()){
				return lista.get(0);
			}
			else{
				return new Tuple();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Tuple> readAll(){
		
		ArrayList<Tuple> tuplas = new ArrayList<Tuple>();
		
		for(int count = 0; count < interests.size(); count++){
			try{
				Pattern pattern1 = (Pattern) new Pattern().addField("ContextKey", interests.get(count));
				List<Tuple> lista = read(pattern1, null);
				if(!lista.isEmpty()){
					tuplas.add(lista.get(0));
				}
				else{
					tuplas.add(new Tuple());
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return tuplas;
	}

	public List<Tuple> take(Pattern pattern, IFilter filter) {
		List<Tuple> tuples = null;

		try {
			tuples = domain.take(pattern, filter, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tuples;
	}
	
	public void takeInterest(String key){
		try{
			Pattern pattern1 = (Pattern)new Pattern().addField("AppId", appId)
				.addField("InterestElement", key);
			take(pattern1, null);
			interests.remove(key);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void takeAllInterests() {
		
		for(int count = 0; count < interests.size(); count++){
			try{
				Pattern pattern1 = (Pattern)new Pattern().addField("AppId", appId)
					.addField("InterestElement", interests.get(count));
				take(pattern1, null);
				interests.remove(interests.get(count));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> getInterests(){
		return interests;
	}

	public String subscribe(IClientReaction reaction, String event,
			Pattern pattern, IFilter filter) {
		ClientReactionWrapper clientReactionWrapper = new ClientReactionWrapper(
				reaction, pattern, filter);

		try {
			clientReactionWrapper.setId(domain.subscribe(clientReactionWrapper,
					event, ""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clientReactionWrapper.getId();

	}
	
	/**
	 * @param reaction
	 * @param event
	 * @param key
	 * @param filter
	 * @return String com id do subscribe.
	 */
	public String subscribe(IClientReaction reaction, String event, String key, IFilter filter){	
		
		String reactionId = "";
		
		Pattern pattern = new Pattern();
		pattern.addField("ContextKey", key);
		try {
			reactionId = subscribe(reaction, event, pattern, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!reactionId.equals("")){
			reactionIds.add(reactionId);
		}
		return reactionId;
	}
	
	/**
	 * @param reaction
	 * @param key
	 * @return String com id do subscribe.
	 */
	public String subscribe(IClientReaction reaction, String key){
		
		
		String reactionId = "";
		
		Pattern pattern = new Pattern();
		pattern.addField("ContextKey", key);
		try {
			reactionId = subscribe(reaction, "put", pattern, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!reactionId.equals("")){
			reactionIds.add(reactionId);
		}
		return reactionId;
	}

	public void unSubscribe(String reactionId) {
		try {
			domain.unsubscribe(reactionId, "");
			reactionIds.remove(reactionId);
		} catch (TupleSpaceException e) {
			e.printStackTrace();
		} catch (TupleSpaceSecurityException e) {
			e.printStackTrace();
		}

	}
	
	public void unSubscribeAll(){
		
		for(String reactionId : reactionIds){
			try {
				unSubscribe(reactionId);
				reactionIds.remove(reactionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public String printLoCCAMState() {
		return loCCAM.printState();
	}

}
