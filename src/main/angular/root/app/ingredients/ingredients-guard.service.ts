import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";

import { AuthorizationService } from "@shared/authorization.service";

@Injectable()
export class IngredientsGuardService implements CanActivate {

  constructor(private authorizationService: AuthorizationService) { }

  canActivate() {
    return this.authorizationService.isIngredientManager();
  }

}
