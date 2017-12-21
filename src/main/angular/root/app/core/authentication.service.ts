import { Injectable } from "@angular/core";
import { environment } from "@env/environment.prod";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders } from "@angular/common/http";

export class Authentication {
  username: string;
  password: string;
}

@Injectable()
export class AuthenticationService {

  private _currentAuthentication: Authentication = null;

  constructor(private http: HttpClient) { }

  get currentAuthentication(): Authentication {
    return this._currentAuthentication;
  }

  authenticate(authentication: Authentication): Observable<boolean> {
    const basicAuth = "Basic " + btoa(authentication.username + ":" + authentication.password);
    const headers: HttpHeaders = new HttpHeaders({"Authorization": basicAuth});
    return this.http.post(environment.authenticationUrl, null, {headers: headers, observe : "response"})
      .map(response => this.handleAuthenticationSuccess(authentication, response))
      .catch(error => Observable.of(false));
  }

  logout(): Observable<boolean> {
    this._currentAuthentication = null;
    return Observable.of(true);
  }

  private handleAuthenticationSuccess(authentication: Authentication, response): boolean {
    const isSuccessfulAuthentication: boolean = response.status === 204;
    if (isSuccessfulAuthentication) {
      this._currentAuthentication = authentication;
    }
    return isSuccessfulAuthentication;
  }

}
