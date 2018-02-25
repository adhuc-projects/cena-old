import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { MatDialogRef } from "@angular/material";

import { IngredientsService, CreateIngredient, IngredientLink } from "@ingredients/ingredients.service";

@Component({
  selector: "cena-ingredient-creation",
  templateUrl: "./ingredient-creation.component.html",
  styleUrls: ["./ingredient-creation.component.css"]
})
export class IngredientCreationComponent implements OnInit {

  @Output() ingredientCreated = new EventEmitter<IngredientLink>();

  ingredient: CreateIngredient = new CreateIngredient();

  constructor(private ingredientService: IngredientsService, public dialogRef: MatDialogRef<IngredientCreationComponent>) { }

  ngOnInit() {
  }

  createIngredient(): void {
    this.ingredientService.createIngredient(this.ingredient).subscribe(ingredientLink => {
      this.ingredientCreated.emit(ingredientLink);
      this.dialogRef.close();
    });
  }

  cancel(): void {
    this.dialogRef.close();
  }

}
