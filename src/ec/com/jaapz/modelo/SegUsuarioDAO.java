package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SegUsuarioDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<SegUsuario> getUsuario(String usuario,String clave) {
		List<SegUsuario> resultado; 
		Query query = getEntityManager().createNamedQuery("SegUsuario.buscarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("usuario", usuario);
		query.setParameter("clave", clave);
		resultado = (List<SegUsuario>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<SegUsuario> getValidarUsuario(String usuario,int idUsuario) {
		List<SegUsuario> resultado; 
		Query query = getEntityManager().createNamedQuery("SegUsuario.validarUsuario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("usuario", usuario);
		query.setParameter("idUsuario", idUsuario);
		resultado = (List<SegUsuario>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<SegUsuario> getListaUsuarios(){
		List<SegUsuario> resultado = new ArrayList<SegUsuario>();
		Query query = getEntityManager().createNamedQuery("SegUsuario.findAll");
		resultado = (List<SegUsuario>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<SegUsuario> getListaUsuariosInspeccion(){
		List<SegUsuario> resultado = new ArrayList<SegUsuario>();
		Query query = getEntityManager().createNamedQuery("SegUsuario.buscarInspeccion");
		resultado = (List<SegUsuario>) query.getResultList();
		return resultado;
	}
}
