import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterModule, RouterOutlet} from '@angular/router';  // Importáld a RouterModule-t
import {FormsModule} from '@angular/forms';
import {CommonModule, DatePipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [RouterOutlet, FormsModule,CommonModule],
})
export class AppComponent {
  title = 'Squishmallow Application';
}
