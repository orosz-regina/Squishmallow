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
        console.error(error);
      }
    );
  }


  loadUsers(): void {
    this.userService.getUsers().subscribe((data: any) => {
      this.users = data;
    });
  }
//user hozzáadása
  addUser() {
    this.userService.addUser(this.newUser).subscribe({
      next: (newUser) => {
        this.users.push(newUser);
        this.newUser = { username: '', email: '', password: ''};
      },
      error: (err) => {
        console.error(err);
        alert(err.error);
      }
    });
  }
// user törlése
  deleteUser(username: string): void {
    this.userService.deleteUser(username).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: (err) => {
        console.error(err);
        alert('Hiba történt a felhasználó törlésekor.');
      }
    });
  }

// user szerkesztése
  editUser(user: User): void {
    this.editingUser = { ...user };
  }

  // Modal megnyitása
  openUpdateModal(user: any) {
    if (user) {
      this.currentUser = { ...user };
      this.isModalOpen = true;
    } else {
      console.error();
    }
  }

  // Modal bezárása
  closeModal() {
    this.isModalOpen = false;
    this.currentUser = {};
  }

  // user frissítése
  updateUser(): void {
    this.userService.updateUser(this.currentUser).subscribe({
      next: (updatedUser) => {
        const index = this.users.findIndex(sm => sm.id === this.currentUser.id);
        if (index !== -1) {
          this.users[index] = updatedUser;
        }
        this.closeModal();
      },
      error: (err) => {
        console.error(err);
        alert('Hiba történt a frissítés során');
      }
    });
  }

  // A Collection gomb
  navigateToCollection(userId: number) {
    this.router.navigate([`/collection/${userId}`]);
  }
}
