import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../../../_service/userCollection.service';
import { Collection } from '../../../_model/userCollection.model';
import { DatePipe, NgForOf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-collection',
  templateUrl: './user-collection.component.html',
  imports: [
    DatePipe,
    FormsModule,
    NgForOf
  ]
})
export class CollectionComponent implements OnInit {
  username: string | undefined;
  collection: Collection[] = [];
  squishmallowIdToAdd!: number | null;
  userId!: number;

  constructor(
    private collectionService: CollectionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['userId']; // Az '+' konvertálja a paramétert számra
      console.log('User ID:', this.userId); // Debugging: ellenőrizd, hogy a userId valóban beállítódik
      if (this.userId) {
        this.getCollection();
      } else {
        console.error('User ID is undefined');
      }
    });
  }

  getCollection(): void {
    if (this.userId) {
      this.collectionService.getUserCollection(this.userId).subscribe(
        (data) => {
          this.collection = data; // A válaszban már benne van a squishmallow adat
          console.log('Collection data:', data); // Debugging: Ellenőrizd a visszaérkező adatokat
        },
        (error) => {
          console.error('Error fetching collection:', error);
        }
      );
    } else {
      console.error('User ID is undefined');
    }
  }

  // Add a squishmallow to the user's collection
  addToCollection(): void {
    if (this.squishmallowIdToAdd) {
      const userId = 1; // Az aktuális felhasználó ID-ja (például globálisan tárolva vagy lekérdezhető)
      this.collectionService.addToCollection(userId, this.squishmallowIdToAdd).subscribe(
        (newItem) => {
          this.collection.push(newItem); // A válaszban már benne van a squishmallow adat
          this.squishmallowIdToAdd = null; // Reset input
        },
        (error) => {
          console.error('Error adding to collection:', error);
        }
      );
    }
  }

  // Delete an item from the collection
  deleteFromCollection(collectionId: number): void {
    const userId = 1; // Az aktuális felhasználó ID-ja
    this.collectionService.deleteFromCollection(userId, collectionId).subscribe(
      () => {
        this.collection = this.collection.filter(item => item.id !== collectionId);
      },
      (error) => {
        console.error('Error deleting from collection:', error);
      }
    );
  }
}
