package ec.com.jaapz.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CuentaClienteDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<CuentaCliente> getListaCuentaClientes(String patron){
		List<CuentaCliente> resultado = new ArrayList<CuentaCliente>();
		Query query = getEntityManager().createNamedQuery("CuentaCliente.findAll");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<CuentaCliente>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<CuentaCliente> getListaCuentasActivas(){
		List<CuentaCliente> resultado = new ArrayList<CuentaCliente>();
		Query query = getEntityManager().createNamedQuery("CuentaCliente.bucarTodos");
		resultado = (List<CuentaCliente>) query.getResultList();
		return resultado;
	}
}
