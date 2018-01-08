import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "@app/app-routing.module";
import { CoreModule } from "@core/core.module";
import { SearchModule } from "@search/search.module";
import { IngredientsModule } from "@ingredients/ingredients.module";

import { AppComponent } from "@app/app.component";
import { SharedModule } from "@shared/shared.module";
import { ApiService } from "@shared/api.service";
import { AuthenticationHolder } from "@shared/authentication.holder";
import { AuthorizationService } from "@shared/authorization.service";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    CoreModule,
    SearchModule,
    IngredientsModule
  ],
  declarations: [
    AppComponent,
  ],
  providers: [
    ApiService,
    AuthenticationHolder,
    AuthorizationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
