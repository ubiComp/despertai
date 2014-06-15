package br.ufc.loccam.adaptation.reasorner;

import br.ufc.loccam.adaptation.model.Component;

public interface IAdaptationReasonerObserver {
	
	/**
	 * Notifica que houve uma altera��o na configura��o desejada da zona de observa��o 
	 * @param observableZone - Configura��o da nova zona de observa��o desejada
	 */
	public void notifyObservableZoneChanged(Component[] addedComponents, Component[] removedComponents);
	
}
