import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../../../_service/userCollection.service';
import { Collection } from '../../../_model/userCollection.model';
import { Squishmallow } from '../../../_model/squishmallow.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../_service/user.service';  // Importáljuk az UserService-t

@Component({
  selector: 'app-collection',
  templateUrl: './user-collection.component.html',
  styleUrls: ['./user-collection.component.css'],
  imports: [
    DatePipe,
    FormsModule,
    NgForOf,
    NgIf
  ]
})
export class CollectionComponent implements OnInit {

  username: string | undefined; // A username tárolása
  collectionData: any[] = [];  // Itt tároljuk a Collection adatokat
  collection: Collection[] = [];
  newSquishmallowToAdd: any = {userId: '', squishmallowId: ''};
  squishmallows: Squishmallow[] = [];
  userId!: number;

  constructor(
    private collectionService: CollectionService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService  // UserService injektálása
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['userId'];  // Az '+' konvertálja a paramétert számra
      console.log('User ID:', this.userId); // Debugging: ellenőrizd, hogy a userId valóban beállítódik
      if (this.userId) {
        // A newSquishmallowToAdd objektumba beállítjuk a userId-t
        this.newSquishmallowToAdd = { userId: this.userId, squishmallowId: '' };

        // Ezután hívhatjuk a megfelelő metódusokat
        this.getCollection();
        this.getSquishmallows();
        this.getUsername();  // Lekérjük a username-t
      } else {
        console.error('User ID is undefined');
      }
    });
  }

  updateSquishmallowId(selectedId: any) {
    this.newSquishmallowToAdd.squishmallowId = Number(selectedId);
  }




  addToCollection(): void {
    console.log('New Squishmallow to add:', this.newSquishmallowToAdd);  // Ellenőrizd az objektumot

    if (this.newSquishmallowToAdd.userId && this.newSquishmallowToAdd.squishmallowId) {
      // Ha a userId és a squishmallowId meg van adva, küldhetjük el a kérést
      this.collectionService.addToCollection(this.newSquishmallowToAdd).subscribe(
        (response) => {
          console.log('Successfully added to collection:', response);
          // Vizsgáld meg a választ, hogy megnézd, van-e benne valami probléma
          if (response && response.success) {
            console.log('Collection updated successfully.');
          } else {
            console.error('Unexpected response format:', response);
          }
          this.getCollection();  // Frissítjük a gyűjteményt
        },
        (error) => {
          console.error('Error adding to collection:', error);
          alert('Failed to add Squishmallow to collection. Please try again.');
        }
      );
    } else {
      // Ha nincs megfelelő adat, hibaüzenet
      console.error('User ID or Squishmallow ID is missing.');
      alert('User ID or Squishmallow ID is missing. Please select a Squishmallow.');
    }
  }




  getCollection(): void {
    this.collectionService.getUserCollection(this.userId).subscribe({
      next: (data) => {
        // Az új adatokat hozzárendeljük a collection változóhoz
        this.collection = data;
        console.log('Collection data:', data);
      },
      error: (err) => {
        // Ha a backend visszaküldte a hibaüzenetet, azt kezeljük
        console.error('Error fetching collection:', err);
        alert(err.error);
      }
    });
  }


  getSquishmallows(): void {
    this.collectionService.getAllSquishmallows().subscribe(
      (data) => {
        this.squishmallows = data;
        console.log('All squishmallows:', data);
      },
      (error) => {
        console.error('Error fetching squishmallows:', error);
      }
    );
  }

  // Lekérjük a felhasználó nevét
  getUsername(): void {
    this.userService.getUsername(this.userId).subscribe(
      (data) => {
        this.username = data.username;  // Beállítjuk a username-t
        console.log('Username:', this.username);  // Debugging
      },
      (error) => {
        console.error('Error fetching username:', error);
        // Itt informálhatod a felhasználót, hogy mi történt
        alert('There was an error fetching the username. Please try again later.');
      }
    );
  }


  // Collection adatok lekérése az új szolgáltatás segítségével



  deleteFromCollection(collectionId: number): void {
    const userId = 1;
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
