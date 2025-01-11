import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  username: string;
  email: string;
  password: string;
  createdAt: Date;
}

@Injectable({
  providedIn: 'root' // Globális szintű szolgáltatás
})
export class UserService {
  private baseUrl = 'http://localhost:8080/users'; // Backend API URL

  constructor(private http: HttpClient) {}

  // 1. Összes felhasználó lekérése
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  // 2. Új felhasználó létrehozása
  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl, user);
  }

  // 3. Felhasználó frissítése
  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, user);
  }

  // 4. Felhasználó törlése
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
