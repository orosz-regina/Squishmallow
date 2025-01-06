import { importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app/app.component';


bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(HttpClientModule)  // Itt adod hozzá az HttpClientModule-t
  ]
})
  .catch(err => console.error(err));
