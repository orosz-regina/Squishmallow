import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Collection } from '../_model/userCollection.model';
import { Squishmallow } from '../_model/squishmallow.model';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  private apiUrl = 'http://localhost:8080/collection';
  private squishmallowApiUrl = 'http://localhost:8080/squishmallows';

  constructor(private http: HttpClient) {}

  getUserCollection(userId: number): Observable<Collection[]> {
    return this.http.get<Collection[]>(`${this.apiUrl}/${userId}`);
  }

  getAllSquishmallows(): Observable<Squishmallow[]> {
    return this.http.get<Squishmallow[]>(this.squishmallowApiUrl);
  }

  addToCollection(SquishmallowToAdd: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add`, SquishmallowToAdd, {
      headers: {'Content-Type': 'application/json'}
    });
  }

    deleteFromCollection(userId: number, collectionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${userId}/${collectionId}`);
  }

}
