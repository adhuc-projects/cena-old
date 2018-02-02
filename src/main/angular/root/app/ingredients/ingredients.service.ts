import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/Observable";

import { ApiService } from "@shared/api.service";

export class Ingredient {

  constructor(public id: string, public name: string) {}

  print() {
    return "Id: " + this.id + ", name: " + this.name;
  }

}

export function convertFromJson(json: any): Ingredient {
  return new Ingredient(json.id, json.name);
}

@Injectable()
export class IngredientsService {

  constructor(private apiService: ApiService, private http: HttpClient) { }

  getIngredients(): Observable<Array<Ingredient>> {
    return this.apiService.getApiIndex().map(resource => resource.getLink(ApiService.INGREDIENTS_MANAGEMENT_LINK_NAME))
      .flatMap(ingredientsUrl => {
        return this.http.get(ingredientsUrl, {observe : "response"})
          .map(response => this.extractIngredients(response))
          .catch(error => Observable.of(new Array<Ingredient>()));
      });
  }

  private extractIngredients(response): Array<Ingredient> {
    const elements = response.body._embedded.data;
    const ingredients = new Array<Ingredient>();
    for (const element of elements) {
      ingredients.push(convertFromJson(element));
    }
    return ingredients;
  }

}
