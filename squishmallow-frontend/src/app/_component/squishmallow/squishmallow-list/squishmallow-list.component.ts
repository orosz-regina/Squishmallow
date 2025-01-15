import { Component, OnInit } from '@angular/core';
import { SquishmallowService } from '../../../_service/squishmallow.service';
import {CommonModule, NgForOf, NgIf} from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-squishmallow-list',
  templateUrl: './squishmallow-list.component.html',
  imports: [
    CommonModule,
    FormsModule,
  ],
  styleUrls: ['./squishmallow-list.component.css']
})
export class SquishmallowListComponent implements OnInit {

  squishmallows: any[] = [];
  newSquishmallow: any = {name: '', type: '', category: '', size: ''};
  currentSquishmallow: any = null;
  isModalOpen = false;

  openModal(squishmallow: any): void {
    if (squishmallow) {
      this.currentSquishmallow = {...squishmallow};
      this.isModalOpen = true;
    } else {
      console.error('Invalid squishmallow data');
    }
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  constructor(private squishmallowService: SquishmallowService) {
  }

  ngOnInit() {
    this.squishmallowService.getSquishmallows().subscribe(data => {
      this.squishmallows = data;
      console.log('Squishmallows:', this.squishmallows);
    });
  }

  //törlés
  deleteSquishmallow(id: number) {
    this.squishmallowService.checkUserCollection(id).subscribe({
      next: (data) => {
        console.error("Benne van: ", data);
        if (data) {
          alert("Ez a Squishmallow már szerepel legalább egy felhasználó gyűjteményében, ezért nem törölhető.");
        } else {
          this.squishmallowService.deleteSquishmallow(id).subscribe({
            next: () => {
              this.squishmallows = this.squishmallows.filter(squishmallow => squishmallow.id !== id);
            },
            error: error => {
              console.error("Failed to delete squishmallow", error);
              alert("Failed to delete squishmallow");
            }
          });
        }
      },
      error: error => {
        console.error("Error checking user collection", error);
        alert("Hiba történt a felhasználói gyűjtemény ellenőrzése során.");
      }
    });
  }

  //squishmallow hozzáadása
  addSquishmallow() {
    this.squishmallowService.addSquishmallow(this.newSquishmallow).subscribe({
      next: (newSquishmallow) => {
        this.squishmallows.push(newSquishmallow);
        this.newSquishmallow = {name: '', type: '', category: '', size: ''};
      },
      error: (err) => {
        console.error('Failed to add squishmallow', err);
        alert(err.error);
      }
    });
  }

  //squishmallow frissítése
  updateSquishmallow(): void {
    this.squishmallowService.updateSquishmallow(this.currentSquishmallow).subscribe({
      next: (updatedSquishmallow) => {
        const index = this.squishmallows.findIndex(sm => sm.id === this.currentSquishmallow.id);
        if (index !== -1) {
          this.squishmallows[index] = updatedSquishmallow;
        }
        this.closeModal();
      },
      error: (err) => {
        console.error('Failed to update squishmallow', err);
        alert('Hiba történt a frissítés során');
      }
    });
  }
}
