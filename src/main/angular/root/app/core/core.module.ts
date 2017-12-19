import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { CookieService } from "ngx-cookie-service";

import { SharedModule } from "@shared/shared.module";

import { AuthenticationComponent } from "@core/authentication/authentication.component";
import {FooterComponent} from "@core/footer/footer.component";
import {HeaderComponent} from "@core/header/header.component";
import { LicenseComponent } from "@core/license/license.component";
import { LanguageSelectionComponent } from "@core/language-selection/language-selection.component";
import { LanguageService } from "@core/language.service";
import { AuthenticationService } from "@core/authentication.service";

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
    }),
    SharedModule
  ],
  declarations: [
    HeaderComponent,
    FooterComponent,
    LicenseComponent,
    LanguageSelectionComponent,
    AuthenticationComponent
  ],
  exports: [
    HeaderComponent,
    FooterComponent
  ],
  providers: [
    CookieService,
    LanguageService,
    AuthenticationService
  ]
})
export class CoreModule { }
