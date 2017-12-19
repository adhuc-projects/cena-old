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

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.authentication = new Authentication();
    this.authentication.username = "";
    this.authentication.password = "";
  }

  authenticationFormFilled(): boolean {
    return this.authentication.username.length > 0 && this.authentication.password.length > 0;
  }

  authenticate() {
    this.authenticationService.authenticate(this.authentication).subscribe(authenticated => {
      if (authenticated) {
        this.saveAuthentication();
      } else {
        this.notifyAuthenticationFailure();
      }
    });
  }

  private saveAuthentication() {
    // TODO save authentication
  }

  private notifyAuthenticationFailure() {
    // TODO notify authentication failed
  }

}
