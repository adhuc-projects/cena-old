import { Component, OnInit } from "@angular/core";

import { IngredientsService, Ingredient } from "@ingredients/ingredients.service";

@Component({
  selector: "cena-ingredients-list",
  templateUrl: "./ingredients-list.component.html",
  styleUrls: ["./ingredients-list.component.css"]
})
export class IngredientsListComponent implements OnInit {

  ingredients: Array<Ingredient>;

  constructor(private ingredientService: IngredientsService) { }

  ngOnInit() {
    this.ingredientService.getIngredients().subscribe(ingredientsList => this.ingredients = ingredientsList);
  }

}
