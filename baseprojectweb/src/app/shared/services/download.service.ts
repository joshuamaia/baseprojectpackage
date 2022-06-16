import { Injectable, Injector } from '@angular/core';

import { Observable } from 'rxjs';
import { UtilService } from 'src/app/shared/services/util.service';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root',
})
export class DownloadService {
  protected http: HttpClient;
  protected apiPath: string;

  constructor(protected injector: Injector) {
    this.apiPath = `${UtilService.BASE_URL}`;
    this.http = injector.get(HttpClient);
  }

  downloadFile(data: any, filename: string, type: string) {
    const blob = new Blob([data], { type: type });
    // const url = window.URL.createObjectURL(blob);
    // window.open(url);
    saveAs(blob, filename);
  }

  downloadReportPdfExpense(nameReport: string): Observable<any> {
    let headers = new HttpHeaders();

    headers = headers.set('Accept', 'application/pdf');

    return this.http.get(`${this.apiPath}/expense-reports/pdf/${nameReport}`, {
      headers: headers,
      responseType: 'blob',
    });
  }

  downloadReportPdfPerson(nameReport: string): Observable<any> {
    let headers = new HttpHeaders();

    headers = headers.set('Accept', 'application/pdf');

    return this.http.get(`${this.apiPath}/person-reports/pdf/${nameReport}`, {
      headers: headers,
      responseType: 'blob',
    });
  }

  downloadReportCsvPerson(): Observable<any> {
    let headers = new HttpHeaders();

    headers = headers.set('Accept', 'text/plain');

    return this.http.get(`${this.apiPath}/person-reports/csv`, {
      headers: headers,
      responseType: 'blob',
    });
  }
}
