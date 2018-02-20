import { Injectable } from "@angular/core";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Authentication, AuthenticationHolder } from "@shared/authentication.holder";

@Injectable()
export class AuthenticationService {

  private static readonly AUTHENTICATION_URL = "/authentication";

  constructor(private http: HttpClient, private authenticationHolder: AuthenticationHolder) { }

  authenticate(authentication: Authentication): Observable<boolean> {
    const headers: HttpHeaders = new HttpHeaders({"Authorization": authentication.authorizationHeaderValue()});
    return this.http.post(AuthenticationService.AUTHENTICATION_URL, null, {headers: headers, observe : "response"})
      .map(response => this.handleAuthenticationSuccess(authentication, response))
      .catch(error => {
        console.log("Authentication failed " +  error);
        return Observable.of(false);
      });
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
