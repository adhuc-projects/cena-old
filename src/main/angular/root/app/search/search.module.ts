import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatFormFieldModule, MatInputModule, MatButtonModule } from "@angular/material";
import { SearchRoutingModule } from "@search/search-routing.module";

import { SearchComponent } from "@search/search.component";

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    SearchRoutingModule
  ],
  declarations: [SearchComponent]
})
export class SearchModule { }
