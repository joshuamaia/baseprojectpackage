package br.com.joshua.baseproject.interfaceadapter;

import br.com.joshua.baseproject.businessrule.GeneratePersonReportCsv;
import br.com.joshua.baseproject.businessrule.GenerateReportPdf;

public interface PersonGatewayReport extends GenerateReportPdf, GeneratePersonReportCsv {

}
