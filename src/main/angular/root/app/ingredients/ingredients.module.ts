import { NgModule } from "@angular/core";

import { SharedModule } from "@shared/shared.module";
import { IngredientsRoutingModule } from "@ingredients/ingredients-routing.module";
import { IngredientsListComponent } from "@ingredients/ingredients-list/ingredients-list.component";

@NgModule({
  imports: [
    SharedModule,
    IngredientsRoutingModule
  ],
  declarations: [IngredientsListComponent]
})
export class IngredientsModule { }
