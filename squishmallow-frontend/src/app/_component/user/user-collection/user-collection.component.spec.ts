import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../../../_service/userCollection.service';
import { Collection } from '../../../_model/userCollection.model';
import { Squishmallow } from '../../../_model/squishmallow.model'; // Importáld a Squishmallow típust
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-collection',
  templateUrl: './user-collection.component.html',
  imports: [
    DatePipe,
    FormsModule
  ]
})
export class CollectionComponent implements OnInit {
  username: string | undefined;
  collection: Collection[] = [];
  squishmallowIdToAdd!: number | null;
  squishmallows: Squishmallow[] = []; // A Squishmallowk listája
  userId!: number;

  constructor(
    private collectionService: CollectionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['userId'];  // Az '+' konvertálja a paramétert számra
      console.log('User ID:', this.userId); // Debugging: ellenőrizd, hogy a userId valóban beállítódik
      if (this.userId) {
        this.getCollection();
        this.getSquishmallows(); // Lekérjük az összes squishmallowt
      } else {
        console.error();
      }
    });
  }

  getCollection(): void {
    if (this.userId) {
      this.collectionService.getUserCollection(this.userId).subscribe(
        (data) => {
          this.collection = data;  // A válasz már tartalmazza a Squishmallow adatokat
          console.log('Collection data:', data);  // Debugging: Ellenőrizd a visszaérkező adatokat
        },
        (error) => {
          console.error(error);
        }
      );
    } else {
      console.error();
    }
  }

  getSquishmallows(): void {
    this.collectionService.getAllSquishmallows().subscribe(
      (data) => {
        this.squishmallows = data; // Az összes squishmallow adatot tároljuk itt
        console.log('All squishmallows:', data);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  addToCollection(SquishmallowToAdd: any): void {
    if (this.squishmallowIdToAdd) {
      const userId = 1;  // Az aktuális felhasználó ID-ja (például globálisan tárolva vagy lekérdezhető)
      this.collectionService.addToCollection(userId, this.squishmallowIdToAdd).subscribe(
        (newItem) => {
          this.collection.push(newItem);  // A válasz már tartalmazza a Squishmallow adatokat
          this.squishmallowIdToAdd = null;  // Reset input
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  deleteFromCollection(collectionId: number): void {
    const userId = 1;  // Az aktuális felhasználó ID-ja
    this.collectionService.deleteFromCollection(userId, collectionId).subscribe(
      () => {
        this.collection = this.collection.filter(item => item.id !== collectionId);
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
