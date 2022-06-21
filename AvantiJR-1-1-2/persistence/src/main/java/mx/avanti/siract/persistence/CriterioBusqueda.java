/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emmanuelle
 */
public class CriterioBusqueda {

/**
 * DefiniciÛn de los tipos de criterios de b˙squeda.
 *
 */
	public enum TipoCriterio {Equal, EqualId, Like, Between, Or, OrProperty, OrBetween , OrList, Order, NotLike, NotNull, NotEqual};

	private List<String> properties;
	private Object value;
	private TipoCriterio type_criterio;

/**
 * Crea un criterio de b˙squeda a partir de una sola propiedad.
 * @param type_criterio
 * @param property
 * @param value
 */
	public CriterioBusqueda(TipoCriterio type_criterio, String property, Object value)
	{
		this.properties = new ArrayList<String>();

		this.properties.add(property);

		this.type_criterio = type_criterio;
		this.value = value;
	}

/**
 * Crea un criterio de b˙squeda a partir de una lista de propiedades.
 * @param type_criterio
 * @param properties
 * @param value
 */	public CriterioBusqueda(TipoCriterio type_criterio, List<String> properties, Object value)
	{
		this.properties = properties;
		this.type_criterio = type_criterio;
		this.value = value;
	}

/**
 *
 * @return Primer propiedad de la lista.
 */
 	public String getProperty() {
		return properties.get(0);
	}

/**
 * Obtiene una propiedad de la lista
 * @param index N˙mero de propiedad en la lista (0 a size-1)
 * @see getNumberOfProperties()
 * @return Propiedad del criterio de b˙squeda
 * @throws IndexOutOfBoundsException
 */
 	public String getProperty(int index) throws IndexOutOfBoundsException{
 		return properties.get(index);
 	}

/**
 *
 * @return Cantidad de propiedades del criterio de b˙squeda.
 */
 	public int getNumberOfProperties(){
 		return properties.size();
 	}

/**
 * @deprecated
 * @see getProperties
 * @return Lista de propiedades
 */
	public List<String> getPropertys() {
		return getProperties();
	}

/**
 * Obtiene la lista de propiedades asociada al criterio de b˙squeda.
 * @return Lista de propiedades
 */
	public List<String> getProperties() {
		return properties;
	}

/**
 *
 * @return Tipo de criterio
 */
	public TipoCriterio getTypeCriterio() {
		return type_criterio;
	}

/**
 *
 * @return Objeto con el valor del criterio de b˙squeda
 */
	public Object getValor() {
		return value;
	}
}


