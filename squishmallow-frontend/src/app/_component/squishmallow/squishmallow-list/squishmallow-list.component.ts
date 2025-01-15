import { Component, OnInit } from '@angular/core';
import { SquishmallowService } from '../../../_service/squishmallow.service';
import {CommonModule, NgForOf, NgIf} from '@angular/common';
import {Observable} from 'rxjs';
import { FormsModule } from '@angular/forms';
import {Squishmallow} from '../../../_model/squishmallow.model';

@Component({
  selector: 'app-squishmallow-list',
  templateUrl: './squishmallow-list.component.html',
  imports: [
    CommonModule, // Ez tartalmazza az NgIf és NgForOf direktívákat
    FormsModule,  // Az [(ngModel)] használatához szükséges
  ],
  styleUrls: ['./squishmallow-list.component.css']
})
export class SquishmallowListComponent implements OnInit {

  squishmallows: any[] = [];
  private apiUrl: any;
  private http: any;
  newSquishmallow: any = {name: '', type: '', category: '', size: ''};
  currentSquishmallow: any = null; // Aktuálisan szerkesztett Squishmallow
  isEditing = false;
  isModalOpen = false; // A modal állapota


  openModal(squishmallow: any): void {
    if (squishmallow) {
      this.currentSquishmallow = {...squishmallow};  // Az aktuális squishmallow adatainak másolása
      this.isModalOpen = true; // A modal megjelenítése
    } else {
      console.error('Invalid squishmallow data');
    }
  }

  closeModal(): void {
    this.isModalOpen = false; // A modal bezárása
  }

  constructor(private squishmallowService: SquishmallowService) {
  }

  ngOnInit() {
    // API hívás
    this.squishmallowService.getSquishmallows().subscribe(data => {
      this.squishmallows = data;  // Az adatokat elmentjük a squishmallows változóba
      console.log('Squishmallows:', this.squishmallows);  // Ellenőrzés
    });
  }


  deleteSquishmallow(id: number) {
    // Először ellenőrizzük, hogy a squishmallow szerepel-e a userCollection táblában
    this.squishmallowService.checkUserCollection(id).subscribe({
      next: (data) => {
        console.error("Benne van: ", data);
        if (data) {
          // Ha létezik a gyűjteményben, felugró ablakot jelenítünk meg
          alert("Ez a Squishmallow már szerepel legalább egy felhasználó gyűjteményében, ezért nem törölhető.");
        } else {
          // Ha nem szerepel, akkor töröljük
          this.squishmallowService.deleteSquishmallow(id).subscribe({
            next: () => {
              // Az adat törlés után frissítjük a listát
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


  addSquishmallow() {
    this.squishmallowService.addSquishmallow(this.newSquishmallow).subscribe({
      next: (newSquishmallow) => {
        // Hozzáadjuk az új elemet a listához
        this.squishmallows.push(newSquishmallow);

        // Az űrlapot visszaállítjuk az alapértelmezett értékekre
        this.newSquishmallow = {name: '', type: '', category: '', size: ''};
      },
      error: (err) => {
        // Ha a backend visszaküldte a hibaüzenetet, azt kezeljük
        console.error('Failed to add squishmallow', err);
        alert(err.error);
      }
    });
  }

  updateSquishmallow(): void {
    // Az adatokat elküldjük a backendnek, hogy frissítse őket az adatbázisban
    this.squishmallowService.updateSquishmallow(this.currentSquishmallow).subscribe({
      next: (updatedSquishmallow) => {
        // Az adatbázis frissítése után frissítsük a listát
        const index = this.squishmallows.findIndex(sm => sm.id === this.currentSquishmallow.id);
        if (index !== -1) {
          this.squishmallows[index] = updatedSquishmallow; // A listában is frissítjük a squishmallow adatokat
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
}
