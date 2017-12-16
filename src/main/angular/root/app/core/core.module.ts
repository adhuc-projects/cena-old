import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { SharedModule } from "@shared/shared.module";

import {HeaderComponent} from "@core/header/header.component";
import {FooterComponent} from "@core/footer/footer.component";

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
    FooterComponent
  ],
  exports: [
    HeaderComponent,
    FooterComponent
  ]
})
export class CoreModule { }
