package br.ufc.loccam.adaptation.reasorner;

import br.ufc.loccam.adaptation.model.Component;

/**
 * @author Andre
 *
 */
public interface IAdaptationReasoner {

	/**
	 * Configura um objeto que ser� notificado sobre novas zonas de observa��es desejadas geradas a partir 
	 * de mudan�as nas zonas de interesse
	 * @param reasonerObserver - Objeto que receber� as notifica��es
	 */
	public void setReasonerObservable(IAdaptationReasonerObserver reasonerObserver);
	
	/**
	 * Adiciona uma nova aplica��o � lista de aplica��es inscritas no LoCCAM
	 * @param application
	 */
	public void addApplication(Component application);
	
	/**
	 * Remove uma aplica��o da listas de aplica��es inscritas no LoCCAM
	 * @param application
	 */
	public void removeApplication(String symbolicName);
	
	/**
	 * Adiciona um novo CAC � lista de CACs dispon�veis para o LoCCAM
	 * @param cac
	 */
	public void addAvailableCAC(Component cac);
	
	/**
	 * Remove um CAC da lista de CACs dispon�veis para o LoCCAM
	 * @param symbolicName - Nome do componente
	 * @param observableContext - zona de observa��o do elemento
	 * @return false, caso n�o seja poss�vel remover o CAC
	 */
	public boolean removeAvailableCAC(String symbolicName, String observableContext);
	
	
	/**
	 * Adiciona um novo interesse a uma aplica��o. Caso a aplica��o ainda n�o exista, ela � criada internamente.
	 * @param appId
	 * @param contextKey
	 */
	public void addApplicationInterestElement(String appId, String contextKey);
	
	/**
	 * Remove um interesse de uma aplica��o
	 * @param appId
	 * @param contextKey
	 */
	public void removeApplicationInterestElement(String appId, String contextKey);
}
