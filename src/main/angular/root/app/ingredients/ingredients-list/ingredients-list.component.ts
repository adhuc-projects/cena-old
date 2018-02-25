import { Component, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material";

import { IngredientsService, Ingredient } from "@ingredients/ingredients.service";
import { IngredientCreationComponent } from "@ingredients/ingredient-creation/ingredient-creation.component";

@Component({
  selector: "cena-ingredients-list",
  templateUrl: "./ingredients-list.component.html",
  styleUrls: ["./ingredients-list.component.css"]
})
export class IngredientsListComponent implements OnInit {

  ingredients: Array<Ingredient>;

  constructor(private ingredientService: IngredientsService, public dialog: MatDialog) { }

  ngOnInit() {
    this.updateIngredientsList();
  }

  openIngredientCreation() {
    const dialogRef = this.dialog.open(IngredientCreationComponent, {
      width: "250px"
    });
    dialogRef.componentInstance.ingredientCreated.subscribe(() => this.updateIngredientsList());
  }

  updateIngredientsList() {
    this.ingredientService.getIngredients().subscribe(ingredientsList => this.ingredients = ingredientsList);
  }

}
