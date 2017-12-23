import { Injectable } from "@angular/core";
import { environment } from "@env/environment.prod";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Authentication, AuthenticationHolder } from "@core/authentication/authentication.holder";

@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient, private authenticationHolder: AuthenticationHolder) { }

  authenticate(authentication: Authentication): Observable<boolean> {
    const headers: HttpHeaders = new HttpHeaders({"Authorization": authentication.authorizationHeaderValue()});
    return this.http.post(environment.authenticationUrl, null, {headers: headers, observe : "response"})
      .map(response => this.handleAuthenticationSuccess(authentication, response))
      .catch(error => Observable.of(false));
  }

  logout(): Observable<boolean> {
    this.authenticationHolder.currentAuthentication = null;
    return Observable.of(true);
  }

  private handleAuthenticationSuccess(authentication: Authentication, response): boolean {
    const isSuccessfulAuthentication: boolean = response.status === 204;
    if (isSuccessfulAuthentication) {
      this.authenticationHolder.currentAuthentication = authentication;
    }
    return isSuccessfulAuthentication;
  }

}
