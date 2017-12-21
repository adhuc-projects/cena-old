import { Component, OnInit } from "@angular/core";
import { FormControl, NgForm } from "@angular/forms";
import { Authentication, AuthenticationService } from "@core/authentication.service";

@Component({
  selector: "cena-authentication",
  templateUrl: "./authentication.component.html",
  styleUrls: ["./authentication.component.css"]
})
export class AuthenticationComponent implements OnInit {

  authentication: Authentication;
  isAuthenticated: boolean;
  authenticatedUser: string;

  constructor(private authenticationService: AuthenticationService) { }

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
    this.authenticatedUser = this.authenticationService.currentAuthentication.username;
  }

  private notifyAuthenticationFailure() {
    // TODO notify authentication failed
    this.initAuthenticationForm();
  }

}
