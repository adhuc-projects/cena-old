import { NgModule } from "@angular/core";

import { SharedModule } from "@shared/shared.module";
import { IngredientsService } from "@ingredients/ingredients.service";
import { IngredientsRoutingModule } from "@ingredients/ingredients-routing.module";
import { IngredientsListComponent } from "@ingredients/ingredients-list/ingredients-list.component";
import { IngredientCreationComponent } from "./ingredient-creation/ingredient-creation.component";

@NgModule({
  imports: [
    SharedModule,
    IngredientsRoutingModule
  ],
  providers: [IngredientsService],
  declarations: [IngredientsListComponent, IngredientCreationComponent],
  entryComponents: [IngredientCreationComponent]
})
export class IngredientsModule { }
