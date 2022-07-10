package br.com.joshua.baseproject.interfaceadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joshua.baseproject.businessrule.GeneratePersonReportCsv;
import br.com.joshua.baseproject.businessrule.GenerateReportPdf;

@Service
public class PersonGatewayReportImpl implements PersonGatewayReport {
	
	@Autowired
	private GenerateReportPdf generateReportPdf;
	
	@Autowired
	private GeneratePersonReportCsv generatePersonReportCsv;

	@Override
	public byte[] generateReportPdf(String nameReport) {
		return generateReportPdf.generateReportPdf(nameReport);
	}

	@Override
	public byte[] generatePersonReportCsv() {
		return generatePersonReportCsv.generatePersonReportCsv();
	}

}
