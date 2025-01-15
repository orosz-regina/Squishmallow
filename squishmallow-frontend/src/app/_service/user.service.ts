import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) {}

  // Összes felhasználó lekérése
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  // Új felhasználó létrehozása
  addUser(user: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, user);
  }

  // Felhasználó frissítése
  updateUser(user: any): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${user.id}`, user);
  }

  // Felhasználó törlése
  deleteUser(username: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${username}`);
  }

  getUsername(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${userId}`);
  }
}
