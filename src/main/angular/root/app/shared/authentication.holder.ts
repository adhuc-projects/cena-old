import { Injectable } from "@angular/core";

export class Authentication {
  username: string;
  password: string;

  authorizationHeaderValue(): string {
    return "Basic " + btoa(this.username + ":" + this.password);
  }
}

@Injectable()
export class AuthenticationHolder {

  private _currentAuthentication: Authentication = null;

  isAuthenticated(): boolean {
    return this._currentAuthentication != null;
  }

  get currentAuthentication(): Authentication {
    return this._currentAuthentication;
  }

  set currentAuthentication(authentication: Authentication) {
    this._currentAuthentication = authentication;
  }

}
