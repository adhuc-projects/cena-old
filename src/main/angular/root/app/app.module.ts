import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "@app/app-routing.module";
import { CoreModule } from "@core/core.module";
import { SearchModule } from "@search/search.module";

import { AppComponent } from "@app/app.component";

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    SearchModule
  ],
  declarations: [
    AppComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
