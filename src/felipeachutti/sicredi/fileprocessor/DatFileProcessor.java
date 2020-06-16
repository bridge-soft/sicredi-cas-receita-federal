package felipeachutti.sicredi.fileprocessor;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import felipeachutti.sicredi.config.ApplicationProperties;
import felipeachutti.sicredi.report.SalesReport;
import felipeachutti.sicredi.vo.Sale;
import felipeachutti.sicredi.vo.SaleItem;
import felipeachutti.sicredi.vo.SalesClient;
import felipeachutti.sicredi.vo.SalesPerson;

public class DatFileProcessor implements FileProcessor {

	private Path filePath;

	private static final String SALESPERSON = "001";
	private static final String CLIENT = "002";
	private static final String SALE = "003";
	private static final String SALE_ITEM_SEPARATOR = ",";
	private static final String SALE_ITEM_DATA_SEPARATOR = "-";
	private static final String DONE_FORMAT = ".done";
	private static final String OUTPUT_FORMAT = "Qtd. Agencias: %s\nQtd. Gerentes: %s\nMelhor venda: %s\nPior resultado venda: Gerente %s";
			
	public DatFileProcessor(Path filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		try (Stream<String> stream = Files.lines(filePath, Charset.defaultCharset())) {
			final SalesReport report = new SalesReport();
			report.setData("");
			SalesReport resultado = stream.reduce(report, (relatorio, linha) -> {
				processarDados(relatorio, linha);
				relatorio.setData(relatorio.getData() + linha);
				return relatorio;
			}, (report1, report2) -> {
				return report1;
			});

			StringBuilder outputNameFile = new StringBuilder();
			outputNameFile.append(System.getenv("HOMEPATH"));
			outputNameFile.append(ApplicationProperties.get("data.output.directory"));
			outputNameFile.append(filePath.getFileName().toString().substring(0, filePath.getFileName().toString().lastIndexOf('.')));
			outputNameFile.append(DONE_FORMAT);
			outputNameFile.append(filePath.getFileName().toString().substring(filePath.getFileName().toString().lastIndexOf('.')));
			
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputNameFile.toString()),Charset.defaultCharset())) {
				String clientsSizeText = resultado.getClients() != null ? Integer.toString(resultado.getClients().size()) : "0";
				String salesSizeText = resultado.getSalesPeople() != null ? Integer.toString(resultado.getSalesPeople().size()) : "0";
				String bestSaleText = resultado.getBestSale() != null ? resultado.getBestSale().getSaleId() : "Não houve melhor venda";
				SalesPerson worstSalesPerson = resultado.getSalesPeople().values().stream().min(Comparator.comparing(SalesPerson::getTotalSales)).orElse(null);
				String worstSalesPersonText = worstSalesPerson != null ? worstSalesPerson.getName() : "Nenhum";
				writer.write(String.format(OUTPUT_FORMAT, clientsSizeText, salesSizeText, bestSaleText, worstSalesPersonText));
			}

		} catch (IOException e) {
			System.err.println(String.format("Erro na leitura do arquivo de dados %s", filePath.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro Desconhecido");
		}
	}

	private void processarDados(SalesReport resultado, String dataLine) {
		String[] data = dataLine.split(ApplicationProperties.get("data.input.separator"));
		if (data.length > 0) {
			switch (data[0]) {
			case SALESPERSON:
				processarVendedor(resultado, data);
				break;
			case CLIENT:
				processarCliente(resultado, data);
				break;
			case SALE:
				processarVenda(resultado, data);
				break;
			default:
				break;
			}
		}
	}

	private void processarVendedor(SalesReport resultado, String[] data) {
		SalesPerson person = new SalesPerson();
		person.setCpf(data[1]);
		person.setName(data[2]);
		person.setSalary(Double.parseDouble(data[3]));
		resultado.addSalesPerson(person);
	}

	private void processarCliente(SalesReport resultado, String[] data) {
		SalesClient client = new SalesClient();
		client.setCnpj(data[1]);
		client.setName(data[2]);
		client.setBusinessArea(data[3]);
		resultado.addClient(client);
	}

	private void processarVenda(SalesReport resultado, String[] data) {
		Sale sale = new Sale();

		sale.setSaleId(data[1]);
		sale.setSalesPerson(data[3]);

		String[] saleItems = data[2].substring(1, data[2].length() - 1).split(SALE_ITEM_SEPARATOR);
		for (String saleItemStr : saleItems) {
			String[] itemData = saleItemStr.split(SALE_ITEM_DATA_SEPARATOR);
			SaleItem saleItem = new SaleItem();
			saleItem.setItemId(itemData[0]);
			saleItem.setQuantity(Integer.parseInt(itemData[1]));
			saleItem.setPrice(Double.parseDouble(itemData[2]));
			sale.addItem(saleItem);
		}

		if (resultado.getBestSale() == null || resultado.getBestSale().getSaleTotal() < sale.getSaleTotal()) {
			resultado.setBestSale(sale);
		}

		SalesPerson salesPerson = resultado.getSalesPeople().get(sale.getSalesPerson());
		if (salesPerson != null) {
			salesPerson.setTotalSales(salesPerson.getTotalSales() + sale.getSaleTotal());
		}
	}

}
