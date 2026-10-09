package model;

import java.math.BigDecimal;

public class ItemRateio {
	

	    private Integer idItemRateio;
	    private Integer idRateio;
	    private Integer idCentroCustos;
	    private BigDecimal percentual;
	    
	    public ItemRateio() {};
	    public ItemRateio(Integer idItemRateio, Integer idRateio, Integer idCentroCustos, BigDecimal percentual) {
	    	this.idItemRateio = idItemRateio;
	    	this.idRateio = idRateio;
	    	this.idCentroCustos = idCentroCustos;
	    	this.percentual = percentual;
	    };
	    public ItemRateio(Integer idRateio, Integer idCentroCustos, BigDecimal percentual) {
	    	this.idRateio = idRateio;
	    	this.idCentroCustos = idCentroCustos;
	    	this.percentual = percentual;
	    }
		public Integer getIdItemRateio() {
			return idItemRateio;
		}
		public void setIdItemRateio(Integer idItemRateio) {
			this.idItemRateio = idItemRateio;
		}
		public Integer getIdRateio() {
			return idRateio;
		}
		public void setIdRateio(Integer idRateio) {
			this.idRateio = idRateio;
		}
		public Integer getIdCentroCustos() {
			return idCentroCustos;
		}
		public void setIdCentroCustos(Integer idCentroCustos) {
			this.idCentroCustos = idCentroCustos;
		}
		public BigDecimal getPercentual() {
			return percentual;
		}
		public void setPercentual(BigDecimal percentual) {
			this.percentual = percentual;
		};
	    
}
