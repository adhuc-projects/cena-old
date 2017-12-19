import { Injectable } from "@angular/core";
import { environment } from "@env/environment.prod";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from "@angular/common/http";

export class Authentication {
  username: string;
  password: string;
}

@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  authenticate(authentication: Authentication): Observable<boolean> {
    const basicAuth = "Basic " + btoa(authentication.username + ":" + authentication.password);
    const headers: HttpHeaders = new HttpHeaders({"Authorization": basicAuth});
    return this.http.post(environment.authenticationUrl, null, {headers: headers, observe : "response"})
      .map(response => response.status === 204)
      .catch(error => Observable.of(false));
  }

}
