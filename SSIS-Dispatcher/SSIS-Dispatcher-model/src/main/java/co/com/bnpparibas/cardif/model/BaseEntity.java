package co.com.bnpparibas.cardif.model;

import java.io.Serializable;
import java.util.Date;

public interface BaseEntity extends Serializable{
	
	public void setFechaCreacion(Date fechaCreacion);

	public Date getFechaCreacion();
	
	public void setFechaActualizacion(Date fechaActualizacion);

	public Date getFechaActualizacion();
	
}
