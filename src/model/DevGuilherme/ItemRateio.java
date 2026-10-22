package model.DevGuilherme;

import java.math.BigDecimal;

public class ItemRateio {
	    private Long idItemRateio;
	    private Long idRateio;
	    private Long idCentroCustos;
	    private BigDecimal percentual;
	    
	    public ItemRateio() {};
	    public ItemRateio(Long idItemRateio, Long idRateio, Long idCentroCustos, BigDecimal percentual) {
	    	this.idItemRateio = idItemRateio;
	    	this.idRateio = idRateio;
	    	this.idCentroCustos = idCentroCustos;
	    	this.percentual = percentual;
	    };
	    public ItemRateio(Long idRateio, Long idCentroCustos, BigDecimal percentual) {
	    	this.idRateio = idRateio;
	    	this.idCentroCustos = idCentroCustos;
	    	this.percentual = percentual;
	    }
		public Long getIdItemRateio() {
			return idItemRateio;
		}
		public void setIdItemRateio(Long idItemRateio) {
			this.idItemRateio = idItemRateio;
		}
		public Long getIdRateio() {
			return idRateio;
		}
		public void setIdRateio(Long idRateio) {
			this.idRateio = idRateio;
		}
		public Long getIdCentroCustos() {
			return idCentroCustos;
		}
		public void setIdCentroCustos(Long idCentroCustos) {
			this.idCentroCustos = idCentroCustos;
		}
		public BigDecimal getPercentual() {
			return percentual;
		}
		public void setPercentual(BigDecimal percentual) {
			this.percentual = percentual;
		};
	    
}
