import { Component, OnInit } from '@angular/core';
import { UserService, User } from '../../../_service/user.service';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  imports: [
    FormsModule,
    NgClass,
    NgForOf,
    NgIf
  ],
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  newUser = { username: '', email: '', password: '' };
  currentUser: any=null;
  isModalOpen = false;
  editingUser: any = null;


  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.userService.getUsers().subscribe(
      data => {
        this.users = data;
      },
      error => {
        console.error('Error fetching users:', error);
      }
    );
  }


  loadUsers(): void {
    this.userService.getUsers().subscribe((data: any) => {
      this.users = data;
    });
  }

  addUser() {
    this.userService.addUser(this.newUser).subscribe({
      next: (newUser) => {
        // Hozzáadjuk az új felhasználót a listához, ha sikeresen mentve lett
        this.users.push(newUser);

        // Az űrlapot visszaállítjuk alapértelmezettre
        this.newUser = { username: '', email: '', password: ''};
      },
      error: (err) => {
        console.error('Failed to add user', err);
        alert(err.error); // A backend által küldött hibaüzenet megjelenítése
      }
    });
  }
// Felhasználó törlése
  deleteUser(username: string): void {
    this.userService.deleteUser(username).subscribe({
      next: () => {
        this.loadUsers();  // Frissítjük a felhasználói listát
      },
      error: (err) => {
        console.error('Error deleting user', err);
        alert('Hiba történt a felhasználó törlésekor.');
      }
    });
  }

// Felhasználó szerkesztése
  editUser(user: User): void {
    this.editingUser = { ...user };  // Az aktuális felhasználó másolata, hogy szerkeszthessük
  }

  // Modal megnyitása
  openUpdateModal(user: any) {
    if (user) {
      this.currentUser = { ...user }; // Az új felhasználó másolatának létrehozása
      this.isModalOpen = true;
    } else {
      console.error('Invalid user data passed to modal');
    }
  }

  // Modal bezárása
  closeModal() {
    this.isModalOpen = false;
    this.currentUser = {}; // Reset the editingUser object to avoid null errors
  }

  // Felhasználó frissítése
  updateUser(): void {
    // Az adatokat elküldjük a backendnek, hogy frissítse őket az adatbázisban
    this.userService.updateUser(this.currentUser).subscribe({
      next: (updatedUser) => {
        // Az adatbázis frissítése után frissítsük a listát
        const index = this.users.findIndex(sm => sm.id === this.currentUser.id);
        if (index !== -1) {
          this.users[index] = updatedUser; // A listában is frissítjük a squishmallow adatokat
        }

        // Bezárjuk a modált
        this.closeModal();
      },
      error: (err) => {
        console.error('Failed to update squishmallow', err);
        alert('Hiba történt a frissítés során');
      }
    });
  }

  // A Collection gomb logikája
  navigateToCollection(userId: number) {
    this.router.navigate([`/collection/${userId}`]);  // Átirányítás a collection oldalra
  }
}
