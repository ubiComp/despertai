package br.ufc.loccam.adaptation.reasorner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;
import br.ufc.loccam.adaptation.model.Component;

public class AdaptationReasoner implements IAdaptationReasoner {

	private Map<String, List<Component>> availableCACs;

	private List<Component> applications;
	private Set<Component> observableZone;
	
	private List<Component> addedComponents;
	private List<Component> removedComponents;
	
	private IAdaptationReasonerObserver reasonerObserver;
	
	public AdaptationReasoner(Map<String, List<Component>> availableCACs) {
		if(availableCACs == null) {
			availableCACs = new HashMap<String, List<Component>>();
		}
		this.availableCACs = availableCACs;
		
		applications = new ArrayList<Component>();
		observableZone = new HashSet<Component>();
		
		addedComponents = new ArrayList<Component>();
		removedComponents = new ArrayList<Component>();
	}
	
	public void setReasonerObservable(IAdaptationReasonerObserver reasonerObserver) {
		this.reasonerObserver = reasonerObserver;
	}
	
	public void addApplication(Component application) {
		addApplication(application, true);
	}

	public void removeApplication(String symbolicName) {
		Component application = null;

		// Retorna caso n�o exista app com o nome
		for (Component app : applications) {
			if(app.getId().equalsIgnoreCase(symbolicName)) {
				application = app;
				break;
			}
		}
		
		// Retorna caso n�o exista app com o nome
		if(application == null)
			return;
		
		removeComponentIfPossible(application);
		
		// Notifica que houve uma altera��o na configura��o desejada da zona de observa��o 
		notifyChangesToObservers();
	}

	public void addAvailableCAC(Component cac) {
		// Verifica se j� existe uma lista com componentes desse tipo e cria uma caso n�o exista
		List<Component> componentes = availableCACs.get(cac.getContextProvided());
		if(componentes == null) {
			componentes = new ArrayList<Component>();
			availableCACs.put(cac.getContextProvided(), componentes);
		}
		componentes.add(cac);
	}

	public boolean removeAvailableCAC(String symbolicName, String observableContext) {
		Component cac = null;

		List<Component> cacs = availableCACs.get(observableContext);
		
		// Retorna caso n�o exista app com o nome
		for (Component c : cacs) {
			if(c.getId().equalsIgnoreCase(symbolicName)) {
				cac = c;
				break;
			}
		}
		
		if(!cac.getUsedBy().isEmpty()) { // Caso n�o seja usado por ninguem, ele poder� ser removido
			removeComponentIfPossible(cac);
			return true;
		} else { // Caso seja usado, n�o poder� ser removido
			return false;
		}
	}
	
	/**
	 * Adiciona uma nova aplica��o � lista de aplica��es inscritas no LoCCAM e notifica eventual mudan�a na zona de observa��o 
	 * caso notifyChanges seja true
	 * @param application - Aplica��o que ser� adicionada
	 * @param notifyChanges - Vari�vel que determina se uma eventual mudan�a na zona de obsera��o desejada deve ser notificada
	 */
	private void addApplication(Component application, boolean notifyChanges) {
		boolean succes = putAtGraph(application);
		
		// Caso n�o tenha obtido sucesso em adicionar as depend�ncias, remove as arestas e componentes adicionados ao grafo.
		if(!succes) {
			removeComponentIfPossible(application);
		} else {
			applications.add(application);
			
			if(notifyChanges) {
				// Notifica que houve uma altera��o na configura��o desejada da zona de observa��o 
				notifyChangesToObservers();
			}
		}
	}
	
	/**
	 * Adiciona um componente ao grafo e � zona de observa��o (observableZone)
	 * @param component
	 * @return
	 */
	private boolean putAtGraph(Component component) {
		return putAtGraph(component, component.getInterestZone());
	}
	
	/**
	 * Adiciona um componente ao grafo e � zona de observa��o (observableZone) considerando a zona de interesse passada por 
	 * par�metro como sendo do componente
	 * @param component - Componente que ser� adicionado
	 * @param interestZone - Zona de interesse a ser considerada para o componente
	 * @return
	 */
	private boolean putAtGraph(Component component, Set<String> interestZone) {
		if(interestZone != null && !interestZone.isEmpty()) {
			// Adiciona componentes de acordo com a zona de interesse do componente
			for (String contextData : interestZone) {

				// Recupera a lista de componentes que prov�em o tipo de contexto requerido
				List<Component> candidateCACs = availableCACs.get(contextData);
				
				// Verifica se existe algum CAC candidato a preencher o interesse do componente a ser adicionado
				if(candidateCACs == null || candidateCACs.isEmpty())
					return false;
				
				boolean success = false;

				// TODO: L�gica que envolva QoS na escolha do melhor componente
				for (Component candidateCAC : candidateCACs) {
					success = putAtGraph(candidateCAC);
					
					// Caso ache uma depend�ncia v�lida
					if(success) {
						// Configura os relacionamentos no gr�fo
						component.addDependencies(candidateCAC);
						candidateCAC.addUsedBy(component);
						break;
					}
				}
				
				// Se n�o encontrar um componente cujas depend�ncias s�o satisfeitas, retorna false
				if(!success) {
					removeComponentIfPossible(component);
					return false;
				}
			}
		}

		// Se n�o for uma aplica��o, adiciona o componente ao conjunto de v�rtices de CACs
		if(!component.isApplication()) {
			observableZone.add(component);
			addedComponents.add(component);
		}
		return true;
	}
	
	/**
	 * Retira o componente C e suas dependencias do grafo, caso seja poss�vel.
	 * @param component - Componente a ser retirado
	 */
	private void removeComponentIfPossible(Component component) {
		// Verifica se o componente n�o � utilizado por alguem para proseguir com a remo��o
		if(component.isApplication() || component.getUsedBy().isEmpty()) {
			
			// Remove o componente do conjunto de CACs do grafo
			if(!component.isApplication()) {
				observableZone.remove(component);
				removedComponents.add(component);
			}
			
			//Recupera todas as depend�ncias do componente
			Set<Component> dependencies = component.getDependencies();
			
			if(!dependencies.isEmpty()) {
				for (Component dependence : dependencies) {
					// Retira o componente da lista de componentes que utilizam a depend�ncia corrente
					dependence.getUsedBy().remove(component);
					
					// Tenta remover a depend�ncia do grafo
					removeComponentIfPossible(dependence);
				}
				// Limpa as depend�ncias do componente.
				dependencies.clear();
			}
		}
		
	}
	
	public void notifyChangesToObservers() {
		if(reasonerObserver != null) {
			Component[] added = new Component[addedComponents.size()];
			addedComponents.toArray(added);
			addedComponents.clear();
			
			Component[] removed = new Component[removedComponents.size()];
			removedComponents.toArray(removed);
			removedComponents.clear();
			
			reasonerObserver.notifyObservableZoneChanged(added, removed);
		}
	}
	
	@Override
	public void addApplicationInterestElement(String appId, String contextKey) {
		Component app = getApp(appId);

		if(app == null) { 
			// Cria a nova aplica��o
			app = new Component(appId, true);
			app.addInterestZoneElement(contextKey);
			addApplication(app);
		} else {
			app.addInterestZoneElement(contextKey);
			
			Set<String> interestZone = new HashSet<String>();
			interestZone.add(contextKey);
			
			boolean success = putAtGraph(app, interestZone);
			
			if(!success) {
				// TODO : Considerar: O que ocorre quando interesse n�o pode ser satisfeito
				;
			} else {
				// Notifica que houve uma altera��o na configura��o desejada da zona de observa��o 
				notifyChangesToObservers();
			}
		}
	}

	@Override
	public void removeApplicationInterestElement(String appId, String contextKey) {
		Component app = getApp(appId);
		
		if(app == null)
			return;
		
		boolean hadElement = app.removeInterestZoneElement(contextKey);

		if(!hadElement)
			return;
		
		Set<Component> dependencies = app.getDependencies();
		
		// Busca qual depend�ncia corresponde contextData removido
		Component componentToRemove = null;
		for (Component c : dependencies) {
			if(c.getContextProvided().equalsIgnoreCase(contextKey))
				componentToRemove = c;
		}
		
		if(componentToRemove != null) {
			// Remove as arestas entre o componente de origem do evento e sua antiga depend�ncia
			app.getDependencies().remove(componentToRemove);
			componentToRemove.getUsedBy().remove(app);
			
			// Tenta remover a antiga depend�ncia do grafo.
			removeComponentIfPossible(componentToRemove);
		}
		
		// Notifica que houve uma altera��o na configura��o desejada da zona de observa��o 
		notifyChangesToObservers();
	}
	
	/**
	 * Encontra o app que corresponde ao appId. Caso n�o encontre nenhuma app, retorna null.
	 * @param appId
	 * @return componente
	 */
	private Component getApp(String appId) {
		for (Component app : applications) {
			if(app.getId().equalsIgnoreCase(appId))
				return app;
		}
		
		return null;
	}
	
	public String printDesiredOZ() {
		String r;

		Log.i("ADAPT TEST", "-----------------------------------------------------------------------------------------");
		Log.i("ADAPT TEST", "[[DESIRED ZO]]");
		Log.i("ADAPT TEST", "Size: " + observableZone.size());
		
		r = "[[DESIRED ZO]]\nSize: " + observableZone.size();
		
		for (Component c : observableZone) {
			Log.i("ADAPT TEST", c.getId());
			r += "" + c.getId();
		}
		
		return r;
	}
}
