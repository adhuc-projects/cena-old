import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";

import { AuthenticationHolder } from "@shared/authentication.holder";
import { ApiService } from "@shared/api.service";

@Injectable()
export class AuthorizationService {

  constructor(private authenticationHolder: AuthenticationHolder, private apiService: ApiService) { }

  public isIngredientManager(): Observable<boolean> {
    return this.authenticationHolder.isAuthenticated() && this.hasAccessTo(ApiService.INGREDIENTS_MANAGEMENT_LINK_NAME);
  }

  private hasAccessTo(resourceName: string): Observable<boolean> {
    return this.apiService.getApiIndex().map(resource => resource.getLink(resourceName) != null);
  }

}
