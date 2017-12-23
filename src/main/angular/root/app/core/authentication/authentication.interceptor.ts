import { Injectable } from "@angular/core";
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders } from "@angular/common/http";
import "rxjs/Rx";
import { Observable } from "rxjs/Observable";

import { Authentication, AuthenticationHolder } from "@core/authentication/authentication.holder";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private authenticationHolder: AuthenticationHolder) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const nextReq = this.appendAuthorizationHeader(req);
    return next.handle(nextReq);
  }

  private appendAuthorizationHeader(req: HttpRequest<any>): HttpRequest<any> {
    const authentication = this.authenticationHolder.currentAuthentication;
    if (authentication != null) {
      return req.clone({ headers: req.headers.set("Authorization", authentication.authorizationHeaderValue()) });
    }
    return req;
  }

}
