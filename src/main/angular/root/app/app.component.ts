import { Component, OnInit } from "@angular/core";
import { TranslateService, LangChangeEvent } from "@ngx-translate/core";

@Component({
  selector: "cena-root",
  templateUrl: "./app.component.html"
})
export class AppComponent implements OnInit {

  title = "cena";
  appLanguage = this.translateService.currentLang;

  constructor(private translateService: TranslateService) {}

  ngOnInit() {
    this.translateService.addLangs(["fr", "en"]);
    // this language will be used as a fallback when a translation isn't found in the current language
    this.translateService.setDefaultLang("fr");
  }

}
