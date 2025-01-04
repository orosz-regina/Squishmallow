import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';  // Importáld a RouterModule-t
import { SquishmallowListComponent } from './_component/squishmallow-list/squishmallow-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [SquishmallowListComponent, RouterModule]  // Add hozzá az imports-hoz
})
export class AppComponent {
  title = 'Squishmallow Application';
}
