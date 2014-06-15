package br.ufc.great.loccam.util;

import java.util.ArrayList;
import java.util.List;

import br.ufc.great.syssu.base.Tuple;
import br.ufc.loccam.adaptation.model.Component;

public class TupleParser {

	/**
	 * Cria uma lista de aplica��es com suas zonas de interesse de acordo com os interesses registrados no SysSU
	 * @param tuples - Tuplas de interesse registradas no SysSU
	 * @return Lista das aplica��es que possuem seus interesses registrados no SysSU
	 */
	public static List<Component> parseToApplications(List<Tuple> tuples) {
		ArrayList<Component> applications = new ArrayList<Component>();
		
		for (Tuple tuple : tuples) {
			parseNewInterestToApplication(applications, tuple);
		}
		
		return applications;
	}
	
	/**
	 * Adiciona um novo interesse em uma aplica��o. Caso a aplica��o ainda n�o exista, ela ser� criada e adicionada a lista.
	 * @param applications - lista de todas as aplica��es registradas no LoCCAM
	 * @param tuple - tupla com a informa��o da nova zona de interesse
	 * @return Lista das aplica��es registradas
	 */
	public static List<Component> parseNewInterestToApplication(List<Component> applications, Tuple tuple) {
		String applicationId = (String)tuple.getField(0).getValue();
		String contextData = (String)tuple.getField(1).getValue();
		
		for (Component application: applications) {
			
			// Se encontrar uma aplica��o adiciona o novo interesse e 
			if(application.getId().equalsIgnoreCase(applicationId)) {
				application.addInterestZoneElement(contextData);
				return applications;
			}
		}
		
		Component app = new Component(applicationId, true);
		app.addInterestZoneElement(contextData);
		applications.add(app);
		
		return applications;
	}
	
	
	// TODO : Verificar no reasoning  qual comportamento ocorre quando aplica��o n�o possui mais interesses
	/**
	 * Retira o interesse de uma aplica��o. Caso a aplica��o n�o possua mais interesses ela � removida da lista
	 * @param applications - lista de todas as aplica��es registradas no LoCCAM
	 * @param tuple - tupla com a informa��o da nova zona de interesse
	 * @return lista das aplica��es registradas
	 */
	public static List<Component> parseRemovedInterestToApplication(List<Component> applications, Tuple tuple) {
		Component app = null;
		String applicationId = (String)tuple.getField(0).getValue();
		String contextData = (String)tuple.getField(1).getValue();
		
		for (Component application: applications) {
			if(application.getId().equalsIgnoreCase(applicationId)) {
				app = application;
				break;
			}
		}
		
		if(app != null) {
			app.removeInterestZoneElement(contextData);
			if(app.getInterestZone().isEmpty())
				applications.remove(app);
		}
		
		return applications;
	}
	
}
