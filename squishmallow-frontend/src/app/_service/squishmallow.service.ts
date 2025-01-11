import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SquishmallowService {

  private apiUrl = 'http://localhost:8080/squishmallows'; // Az API URL

  constructor(private http: HttpClient) { }

  // Squishmallowok listázása
  getSquishmallows(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Squishmallow hozzáadása
  addSquishmallow(squishmallow: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, squishmallow);
  }

  // Squishmallow törlése
  deleteSquishmallow(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Ellenőrizzük, hogy a squishmallow szerepel-e a userCollection-ben
  checkUserCollection(squishmallowId: number): Observable<{ exists: boolean }> {
    return this.http.get<{ exists: boolean }>(`${this.apiUrl}/check-usercollection/${squishmallowId}`);
  }
  // A Squishmallow frissítése
  updateSquishmallow(squishmallow: any): Observable<any> {
    const url = `${this.apiUrl}/${squishmallow.id}`;
    return this.http.put<any>(url, squishmallow);
  }
}
