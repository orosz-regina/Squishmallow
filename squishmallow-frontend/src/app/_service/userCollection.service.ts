import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Collection } from '../_model/userCollection.model';
import { Squishmallow } from '../_model/squishmallow.model'; // Importáld a Squishmallow típust

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  private apiUrl = 'http://localhost:8080/collection'; // Az API végpontja
  private squishmallowApiUrl = 'http://localhost:8080/squishmallows'; // A Squishmallowk API-ja

  constructor(private http: HttpClient) {}

  getUserCollection(userId: number): Observable<Collection[]> {
    return this.http.get<Collection[]>(`${this.apiUrl}/${userId}`);
  }

  getAllSquishmallows(): Observable<Squishmallow[]> {
    return this.http.get<Squishmallow[]>(this.squishmallowApiUrl); // Lekérjük az összes squishmallowt
  }

  addToCollection(SquishmallowToAdd: any): Observable<any> {
    console.log('New Squishmallow to add:', SquishmallowToAdd);
    console.log('Sending JSON:', JSON.stringify(SquishmallowToAdd)); // Ellenőrizd, hogy mi kerül elküldésre

    return this.http.post<any>(`${this.apiUrl}/add`, SquishmallowToAdd, {
      headers: {'Content-Type': 'application/json'}
    });
  }

    deleteFromCollection(userId: number, collectionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${userId}/${collectionId}`);
  }

}
