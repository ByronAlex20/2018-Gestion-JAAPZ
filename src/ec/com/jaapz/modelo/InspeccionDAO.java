package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import ec.com.jaapz.util.Context;

public class InspeccionDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Inspeccion> getListaInspeccion(String patron){
		List<Inspeccion> resultado = new ArrayList<Inspeccion>();
		Query query = getEntityManager().createNamedQuery("Inspeccion.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Inspeccion>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Inspeccion> getListaAllInspeccion(String patron){
		List<Inspeccion> resultado = new ArrayList<Inspeccion>();
		Query query = getEntityManager().createNamedQuery("Inspeccion.buscarInspeccionesTodas");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Inspeccion>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Inspeccion> getListaInspeccionPendiente(String patron){
		List<Inspeccion> resultado = new ArrayList<Inspeccion>();
		Query query = getEntityManager().createNamedQuery("Inspeccion.findAllPendiente");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Inspeccion>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Inspeccion> getListaInspeccionPerfil(String patron){
		List<Inspeccion> resultado = new ArrayList<Inspeccion>();
		Query query = getEntityManager().createNamedQuery("Inspeccion.buscarInspeccionPerfil");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		query.setParameter("idPerfilUsuario", Context.getInstance().getIdUsuario());
		resultado = (List<Inspeccion>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Inspeccion> getListaInspeccionPerfilPendiente(String patron){
		List<Inspeccion> resultado = new ArrayList<Inspeccion>();
		Query query = getEntityManager().createNamedQuery("Inspeccion.buscarInspeccionPerfilPendiente");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		query.setParameter("idPerfilUsuario", Context.getInstance().getIdUsuario());
		resultado = (List<Inspeccion>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Inspeccion> getListaInspeccionAsignada(Integer patron){
		List<Inspeccion> resultado = new ArrayList<Inspeccion>();
		Query query = getEntityManager().createNamedQuery("Inspeccion.buscarInspeccionAsignada");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idPerfilUsuario", patron );
		resultado = (List<Inspeccion>) query.getResultList();
		return resultado;
	}
}
