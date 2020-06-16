package felipeachutti.sicredi.report;

import java.util.HashMap;
import java.util.Map;

import felipeachutti.sicredi.vo.Sale;
import felipeachutti.sicredi.vo.SalesClient;
import felipeachutti.sicredi.vo.SalesPerson;

public class SalesReport {

	private Map<String, SalesPerson> salesPeople;
	private Map<String, SalesClient> clients;
	private Sale bestSale;
	private String data;

	public Map<String, SalesPerson> getSalesPeople() {
		return salesPeople;
	}

	public void setSalesPeople(Map<String, SalesPerson> salesPeople) {
		this.salesPeople = salesPeople;
	}

	public Map<String, SalesClient> getClients() {
		return clients;
	}

	public void setClients(Map<String, SalesClient> clients) {
		this.clients = clients;
	}

	public Sale getBestSale() {
		return bestSale;
	}

	public void setBestSale(Sale bestSale) {
		this.bestSale = bestSale;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void addSalesPerson(SalesPerson person){
		if(salesPeople == null){
			salesPeople = new HashMap<String, SalesPerson>();
		}
		salesPeople.put(person.getName(), person);
	}
	
	public void addClient(SalesClient client){
		if(clients == null){
			clients = new HashMap<String, SalesClient>();
		}
		clients.put(client.getCnpj(), client);
	}
}
