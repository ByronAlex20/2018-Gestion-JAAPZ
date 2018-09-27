package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class AperturaLecturaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<AperturaLectura> getListaAperturas(){
		List<AperturaLectura> resultado = new ArrayList<AperturaLectura>();
		Query query = getEntityManager().createNamedQuery("AperturaLectura.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<AperturaLectura>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<AperturaLectura> getListaAperturasEnProceso(){
		List<AperturaLectura> resultado = new ArrayList<AperturaLectura>();
		Query query = getEntityManager().createNamedQuery("AperturaLectura.buscarCiclo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<AperturaLectura>) query.getResultList();
		return resultado;
	}
}
