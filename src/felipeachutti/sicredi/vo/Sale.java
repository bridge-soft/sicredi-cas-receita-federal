package felipeachutti.sicredi.vo;

import java.util.ArrayList;
import java.util.List;

public class Sale {

	private String saleId;
	private List<SaleItem> items;
	private String salesPerson;

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public List<SaleItem> getItems() {
		return items;
	}

	public void setItems(List<SaleItem> items) {
		this.items = items;
	}
	
	public void addItem(SaleItem item){
		if(items == null){
			items = new ArrayList<SaleItem>();
		}
	}
	
	public double getSaleTotal(){
		double result = 0;
			for(SaleItem item : items){
				result += item.getPrice() * item.getQuantity();
			}
		return result;
		
	}
}
