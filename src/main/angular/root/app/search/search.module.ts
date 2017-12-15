import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SearchComponent } from "@search/search.component";
import { SearchRoutingModule } from "@search/search-routing.module";

@NgModule({
  imports: [
    CommonModule,
    SearchRoutingModule
  ],
  declarations: [SearchComponent]
})
export class SearchModule { }
