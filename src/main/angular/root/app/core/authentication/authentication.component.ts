import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { FormControl, NgForm } from "@angular/forms";

import { Authentication, AuthenticationHolder } from "@core/authentication/authentication.holder";
import { AuthenticationService } from "@core/authentication/authentication.service";
import { ApiService } from "@shared/api.service";

@Component({
  selector: "cena-authentication",
  templateUrl: "./authentication.component.html",
  styleUrls: ["./authentication.component.css"]
})
export class AuthenticationComponent implements OnInit {

  @Output() authenticationChange = new EventEmitter<boolean>();

  authentication: Authentication;
  isAuthenticated: boolean;
  authenticatedUser: string;

  constructor(
    private authenticationHolder: AuthenticationHolder,
    private authenticationService: AuthenticationService,
    private apiService: ApiService) { }

  ngOnInit() {
    this.initAuthenticationForm();
  }

  authenticationFormFilled(): boolean {
    return this.authentication.username.length > 0 && this.authentication.password.length > 0;
  }

  authenticate() {
    this.authenticationService.authenticate(this.authentication).subscribe(authenticated => {
      if (authenticated) {
        this.handleAuthenticationSuccess();
      } else {
        this.notifyAuthenticationFailure();
      }
    });
  }

  logout() {
    this.authenticationService.logout().subscribe(logout => {
      if (logout) {
        this.initAuthenticationForm();
        this.apiService.resetApiResource();
        this.authenticationChange.emit(false);
      }
    });
  }

  private initAuthenticationForm() {
    this.authentication = new Authentication();
    this.authentication.username = "";
    this.authentication.password = "";
    this.isAuthenticated = false;
    this.authenticatedUser = "";
  }

  private handleAuthenticationSuccess() {
    this.isAuthenticated = true;
    this.authenticatedUser = this.authenticationHolder.currentAuthentication.username;
    this.apiService.resetApiResource();
    this.authenticationChange.emit(true);
  }

  private notifyAuthenticationFailure() {
    // TODO notify authentication failed
    this.initAuthenticationForm();
  }

}
