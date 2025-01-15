import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../../../_service/userCollection.service';
import { Collection } from '../../../_model/userCollection.model';
import { Squishmallow } from '../../../_model/squishmallow.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../_service/user.service';

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

  username: string | undefined;
  collection: Collection[] = [];
  newSquishmallowToAdd: any = {userId: '', squishmallowId: ''};
  squishmallows: Squishmallow[] = [];
  userId!: number;

  constructor(
    private collectionService: CollectionService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['userId'];
      console.log('User ID:', this.userId);
      if (this.userId) {
        this.newSquishmallowToAdd = { userId: this.userId, squishmallowId: '' };
        this.getCollection();
        this.getSquishmallows();
        this.getUsername();
      } else {
        console.error('User ID is undefined');
      }
    });
  }

  updateSquishmallowId(selectedId: any) {
    this.newSquishmallowToAdd.squishmallowId = Number(selectedId);
  }

//collectionhoz ad
  addToCollection(): void {
    if (this.newSquishmallowToAdd.userId && this.newSquishmallowToAdd.squishmallowId) {
      this.collectionService.addToCollection(this.newSquishmallowToAdd).subscribe(
        (response) => {
          console.log('Successfully added to collection:', response);
          if (response && response.success) {
            console.log('Collection updated successfully.');
          } else {
            console.error('Unexpected response format:', response);
          }
          this.getCollection();
        },
        (error) => {
          console.error('Error adding to collection:', error);
          alert('Failed to add Squishmallow to collection. Please try again.');
        }
      );
    } else {
      console.error('User ID or Squishmallow ID is missing.');
      alert('User ID or Squishmallow ID is missing. Please select a Squishmallow.');
    }
  }

//user collectionját lekéri
  getCollection(): void {
    this.collectionService.getUserCollection(this.userId).subscribe({
      next: (data) => {
        this.collection = data;
      },
      error: (err) => {
        console.error('Error fetching collection:', err);
        alert(err.error);
      }
    });
  }
  //squishmallow lista lekérése
  getSquishmallows(): void {
    this.collectionService.getAllSquishmallows().subscribe(
      (data) => {
        this.squishmallows = data;
      },
      (error) => {
        console.error('Error fetching squishmallows:', error);
      }
    );
  }

  // Lekérjük a user nevét
  getUsername(): void {
    this.userService.getUsername(this.userId).subscribe(
      (data) => {
        this.username = data.username;
      },
      (error) => {
        console.error('Error fetching username:', error);
        alert('There was an error fetching the username. Please try again later.');
      }
    );
  }
//squsihmallow törlése collectionból
  deleteFromCollection(collectionId: number): void {
    this.collectionService.deleteFromCollection(this.userId, collectionId).subscribe(
      () => {
        this.collection = this.collection.filter(item => item.id !== collectionId);
        console.log('Squishmallow deleted successfully.');
      },
      (error) => {
        console.error('Error deleting from collection:', error);
        alert('Failed to delete Squishmallow from collection. Please try again.');
      }
    );
  }
}
