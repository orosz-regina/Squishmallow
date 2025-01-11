import { Component, OnInit } from '@angular/core';
import { UserService, User } from '../../../_service/user.service';
import {NgForOf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  imports: [
    NgForOf,
    FormsModule
  ],
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  newUser = { username: '', email: '', password: '' };

  constructor(private userService: UserService) {}

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




}
