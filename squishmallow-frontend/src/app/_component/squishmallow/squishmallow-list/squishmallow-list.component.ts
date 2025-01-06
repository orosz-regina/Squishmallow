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
    NgForOf,
    FormsModule
  ],
  styleUrls: ['./squishmallow-list.component.css']
})
export class SquishmallowListComponent implements OnInit {

  squishmallows: any[] = [];
  private apiUrl: any;
  private http: any;
  newSquishmallow: any = { name: '', type: '', category: '', size: '' };

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
        console.error('Failed to add squishmallow', err);
      }
    });
  }
}
