import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Collection } from '../_model/userCollection.model';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  private apiUrl = 'http://localhost:8080/collection'; // Az API végpontja

  constructor(private http: HttpClient) {}

  getUserCollection(userId: number): Observable<Collection[]> {
    return this.http.get<Collection[]>(`${this.apiUrl}/${userId}`);
  }

  addToCollection(userId: number, squishmallowId: number): Observable<Collection> {
    return this.http.post<Collection>(`${this.apiUrl}/add`, { userId, squishmallowId });
  }

  deleteFromCollection(userId: number, collectionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${userId}/${collectionId}`);
  }
}
